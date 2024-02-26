package com.lisade.togeduck.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateResponse {

    Double sourceXPos;
    Double sourceYPos;
    Double destinationXPos;
    Double destinationYPos;
    String source;
    String destination;
    @JsonFormat(pattern = "HH:mm")
    LocalTime departureAt;
    @JsonFormat(pattern = "HH:mm")
    LocalTime arrivalAt;
    @JsonFormat(pattern = "yyyy.MM.dd")
    LocalDateTime startedAt;
    Integer reservedSeats;
    Integer totalSeats;
    Integer price;

    public CoordinateResponse(Double sourceXPos, Double sourceYPos, Double destinationXPos,
        Double destinationYPos, String source, String destination, LocalDateTime routeStartedAt,
        LocalTime expectedTime, Integer reservedSeats, Integer totalSeats, Integer price,
        LocalDateTime startedAt) {
        this.sourceXPos = sourceXPos;
        this.sourceYPos = sourceYPos;
        this.destinationXPos = destinationXPos;
        this.destinationYPos = destinationYPos;
        this.source = source;
        this.destination = destination;
        this.departureAt = routeStartedAt.toLocalTime();
        this.arrivalAt = routeStartedAt.toLocalTime().plusHours(expectedTime.getHour())
            .plusMinutes(expectedTime.getMinute());
        this.startedAt = startedAt;
        this.reservedSeats = reservedSeats;
        this.totalSeats = totalSeats;
        this.price = price;
    }
}
