package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QBus.bus;
import static com.lisade.togeduck.entity.QRoute.route;
import static com.lisade.togeduck.entity.QSeat.seat;

import com.lisade.togeduck.dto.response.BusLayoutDto;
import com.lisade.togeduck.dto.response.SeatDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SeatRepositoryImpl implements SeatRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SeatDto> findSeatsByRouteId(Long routeId) {
        return queryFactory.select(Projections.constructor(
                SeatDto.class,
                seat.id, seat.no, seat.status))
            .from(route)
            .join(seat)
            .on(seat.route.id.eq(route.id))
            .where(route.id.eq(routeId))
            .fetch();
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
