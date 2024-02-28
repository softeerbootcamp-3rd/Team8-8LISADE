package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.request.RouteRegistrationRequest;
import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.dto.response.RouteDetailResponse;
import com.lisade.togeduck.dto.response.RouteRegistrationResponse;
import com.lisade.togeduck.entity.Bus;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Station;
import com.lisade.togeduck.entity.enums.RouteStatus;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RouteMapper {

    public static Route toRoute(
        Festival festival,
        Bus bus,
        Station station,
        RouteRegistrationRequest routeRegistrationRequest,
        Integer price
    ) {
        int expectedHour = routeRegistrationRequest.getDistance() / 60 / 60;
        int expectedMinute = routeRegistrationRequest.getDistance() % (60 * 60) / 60;

        LocalTime expectedTime = LocalTime.of(expectedHour, expectedMinute);
        LocalDateTime startedAt = festival.getStartedAt().minusHours(expectedHour + 1)
            .minusMinutes(expectedMinute);

        return Route.builder()
            .bus(bus)
            .festival(festival)
            .price(price)
            .distance(routeRegistrationRequest.getDistance())
            .station(station)
            .status(RouteStatus.RECRUIT)
            .expectedTime(expectedTime)
            .startedAt(startedAt)
            .numberOfSeats(bus.getNumberOfSeats())
            .numberOfReservationSeats(0)
            .build();
    }

    public static RouteRegistrationResponse toRouteRegistrationResponse(Route route) {
        return RouteRegistrationResponse.builder()
            .routeId(route.getId())
            .festivalId(route.getFestival().getId())
            .build();
    }

    public static RouteDetailResponse toRouteDetailResponse(RouteDetailDto route,
        LocalTime arrivalAt) {
        LocalTime expectedAt = route.getExpectedAt();
        LocalDateTime startedAt = route.getStartedAt();
        return RouteDetailResponse.builder()
            .id(route.getId())
            .startedAt(startedAt)
            .source(route.getSource())
            .destination(route.getDestination())
            .expectedAt(expectedAt)
            .arrivalAt(arrivalAt)
            .totalSeats(route.getTotalSeats())
            .reservedSeats(route.getReservedSeats())
            .cost(route.getCost())
            .build();
    }
}
