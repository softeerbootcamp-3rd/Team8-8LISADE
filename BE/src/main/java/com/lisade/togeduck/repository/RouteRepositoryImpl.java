package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QBus.bus;
import static com.lisade.togeduck.entity.QFestival.festival;
import static com.lisade.togeduck.entity.QFestivalImage.festivalImage;
import static com.lisade.togeduck.entity.QRoute.route;
import static com.lisade.togeduck.entity.QSeat.seat;
import static com.lisade.togeduck.entity.QStation.station;
import static com.lisade.togeduck.entity.QUser.user;
import static com.lisade.togeduck.entity.QUserRoute.userRoute;

import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDto;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<RouteDetailDto> findRouteDetail(Long routeId, Long festivalId) {
        RouteDetailDto routeDetailDto = queryFactory.select(Projections.constructor(
                RouteDetailDto.class,
                route.id,
                route.startedAt,
                route.station.name,
                route.festival.location,
                route.expectedTime,
                getTotalSeats(routeId),
                getReservationSeats(routeId),
                route.price
            )).from(route)
            .join(route.station)
            .join(route.festival)
            .where(route.id.eq(routeId).and(festival.id.eq(festivalId)))
            .fetchOne();
        return Optional.ofNullable(routeDetailDto);
    }

    @Override
    public Slice<UserReservedRouteDto> findReservedRoutes(Pageable pageable, Long userId) {
        List<UserReservedRouteDto> userReservedRoutes = queryFactory.select(Projections.constructor(
                UserReservedRouteDto.class,
                route.id,
                festival.title,
                route.startedAt,
                festival.location,
                station.name,
                route.price,
                bus.numberOfSeats,
                getReservationSeats(),
                festivalImage.path))
            .from(route)
            .join(station)
            .on(route.station.eq(station))
            .join(festival)
            .on(festival.eq(route.festival))
            .join(bus)
            .on(route.bus.eq(bus))
            .join(festivalImage)
            .on(festivalImage.festival.eq(festival)
                .and(festivalImage.id.eq(getMinFestivalImageId())))
            .where(route.id.in((getRouteId(userId))))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1) // 다음 페이지가 있는지 확인
            .fetch();
        boolean hasNext = false;
        if (userReservedRoutes.size() > pageable.getPageSize()) {
            userReservedRoutes.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(userReservedRoutes, pageable, hasNext);
    }

    @Override
    public Optional<UserReservedRouteDetailDto> findReservedRouteInfo(Long userId, Long routeId) {
        return Optional.empty();
    }

    private JPQLQuery<Long> getMinFestivalImageId() {
        return JPAExpressions.select(festivalImage.id.min())
            .from(festivalImage)
            .where(festivalImage.festival.id.eq(festival.id));
    }

    private JPQLQuery<Integer> getTotalSeats(Long routeId) {
        return JPAExpressions.select(bus.numberOfSeats).from(route)
            .where(route.id.eq(routeId))
            .join(bus)
            .on(route.bus.eq(bus));
    }

    private JPQLQuery<Long> getReservationSeats() {
        return JPAExpressions.select(seat.id.count())
            .from(seat)
            .where(seat.route.id.eq(route.id)
                .and(seat.status.eq(SeatStatus.RESERVATION)));
    }

    private JPQLQuery<Long> getReservationSeats(Long routeId) {
        return JPAExpressions.select(seat.id.count())
            .from(seat)
            .where(seat.route.id.eq(routeId)
                .and(seat.status.eq(SeatStatus.RESERVATION)));
    }

    public JPQLQuery<Long> getRouteId(Long userId) {
        return JPAExpressions.select(userRoute.route.id)
            .from(user)
            .join(userRoute)
            .on(userRoute.user.eq(user))
            .where(user.id.eq(userId));
    }
}
