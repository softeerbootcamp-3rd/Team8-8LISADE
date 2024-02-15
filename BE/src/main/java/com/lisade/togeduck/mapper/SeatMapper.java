package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.BusLayoutDto;
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
            .status(seat.getStatus())
            .build();
    }

    public static SeatListDto toSeatListDto(BusLayoutDto busLayoutDto, List<SeatDto> seats) {
        return SeatListDto.builder()
            .numberOfSeats(busLayoutDto.getNumberOfSeats())
            .row(busLayoutDto.getRow())
            .col(busLayoutDto.getCol())
            .backSeats(busLayoutDto.getBackSeats())
            .seats(seats)
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
