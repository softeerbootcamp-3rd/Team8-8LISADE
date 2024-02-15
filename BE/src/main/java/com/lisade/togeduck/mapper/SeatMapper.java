package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.SeatDto;
import com.lisade.togeduck.dto.response.SeatListDto;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Seat;
import com.lisade.togeduck.entity.enums.SeatStatus;
import java.util.List;
import java.util.stream.Stream;

public class SeatMapper {

    public static SeatDto toSeatDto(Seat seat) {
        return SeatDto.builder()
            .id(seat.getId())
            .seatNo(seat.getNo())
            .status(seat.getStatus().toString())
            .build();
    }

    public static SeatListDto toSeatListDto(List<Seat> seats) {
        Route route = seats.get(0).getRoute();

        return SeatListDto.builder()
            .numberOfSeats(seats.size())
            .row(route.getBus().getRow())
            .col(route.getBus().getColumn())
            .backSeats(route.getBus().getBackSeats())
            .seats(seats.stream()
                .map(SeatMapper::toSeatDto)
                .toList())
            .build();
    }

    public static List<Seat> toSeats(Route route, Integer numberOfSeats) {
        return Stream.iterate(1, no -> no + 1)
            .map((no) -> Seat.builder()
                .no(no)
                .route(route)
                .status(SeatStatus.AVAILABLE)
                .build())
            .limit(numberOfSeats)
            .toList();
    }
}
