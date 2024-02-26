package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QFestival.festival;
import static com.lisade.togeduck.entity.QRoute.route;
import static com.lisade.togeduck.entity.QSeat.seat;
import static com.lisade.togeduck.entity.QStation.station;

import com.lisade.togeduck.dto.response.UserTicketResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public UserTicketResponse getTicket(Long userId, Long routeId) {
        return queryFactory.select(Projections.constructor(UserTicketResponse.class,
                route.festival.title,
                route.festival.startedAt,
                route.station.city.name,
                route.station.name,
                route.festival.city.name,
                route.festival.location,
                route.startedAt,
                route.expectedTime,
                seat.no))
            .from(route)
            .join(route.station, station)
            .join(route.festival, festival)
            .join(route.seats, seat)
            .where(seat.user.id.eq(userId).and(route.id.eq(routeId).and(seat.route.id.eq(routeId))))
            .fetchOne();
    }
}
