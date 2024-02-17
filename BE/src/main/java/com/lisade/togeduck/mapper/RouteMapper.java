package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.dto.response.RouteDetailResponse;
import com.lisade.togeduck.dto.response.RouteRegistrationDto;
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
        com.lisade.togeduck.dto.request.RouteRegistrationDto routeRegistrationDto,
        Integer price
    ) {
        int expectedHour = routeRegistrationDto.getExpectedTime() / 60 / 60;
        int expectedMinute = routeRegistrationDto.getExpectedTime() % (60 * 60) / 60;

        LocalTime expectedTime = LocalTime.of(expectedHour, expectedMinute);
        LocalDateTime startedAt = festival.getStartedAt().minusHours(expectedHour + 1)
            .minusMinutes(expectedMinute);

        return Route.builder()
            .bus(bus)
            .festival(festival)
            .price(price)
            .distance(routeRegistrationDto.getDistance())
            .station(station)
            .status(RouteStatus.PROGRESS)
            .expectedTime(expectedTime)
            .startedAt(startedAt)
            .build();
    }

    public static RouteRegistrationDto toRouteRegistrationDto(Route route) {
        return RouteRegistrationDto.builder()
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
