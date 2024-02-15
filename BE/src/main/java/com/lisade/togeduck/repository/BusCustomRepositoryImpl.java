package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QBus.bus;
import static com.lisade.togeduck.entity.QPriceTable.priceTable;
import static com.lisade.togeduck.entity.QRoute.route;

import com.lisade.togeduck.dto.response.BusLayoutDto;
import com.lisade.togeduck.dto.response.DistancePricesDto.BusInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BusCustomRepositoryImpl implements BusCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BusInfo> findBusInfoByDistance(Integer distance) {
        return queryFactory
            .select(
                Projections.constructor(
                    BusInfo.class,
                    bus.id,
                    bus.type.stringValue(),
                    priceTable.price))
            .from(bus)
            .innerJoin(priceTable)
            .on(bus.eq(priceTable.bus)
                .and(priceTable.distance.in(getPriceOfBusesByDistance(distance))))
            .fetch();
    }

    private JPQLQuery<Integer> getPriceOfBusesByDistance(Integer distance) {
        return JPAExpressions
            .select(priceTable.distance.max())
            .from(priceTable)
            .where(priceTable.distance.lt(distance))
            .groupBy(priceTable.bus.id);
    }

    @Override
    public BusLayoutDto findBusLayoutByRouteId(Long routeId) {
        return queryFactory.select(Projections.constructor(BusLayoutDto.class,
                bus.numberOfSeats, bus.row, bus.column, bus.backSeats))
            .from(route)
            .join(bus)
            .on(route.bus.eq(bus))
            .where(route.id.eq(routeId))
            .fetchOne();
    }
}
