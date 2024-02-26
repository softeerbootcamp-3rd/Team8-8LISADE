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
public class UserTicketResponse {

    String title;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    LocalDateTime startedAt;
    String sourceCity;
    String source;
    String destinationCity;
    String destination;
    LocalTime departureAt;
    LocalTime arrivalAt;
    Integer seatNo;

    public UserTicketResponse(String title, LocalDateTime startedAt, String sourceCity,
        String source,
        String destinationCity, String destination, LocalDateTime departureAt,
        LocalTime expectedAt,
        Integer seatNo) {
        this.title = title;
        this.startedAt = startedAt;
        this.sourceCity = sourceCity;
        this.source = source;
        this.destinationCity = destinationCity;
        this.destination = destination;
        this.departureAt = departureAt.toLocalTime();
        this.arrivalAt = departureAt.toLocalTime().plusHours(expectedAt.getHour())
            .plusMinutes(expectedAt.getMinute());
        this.seatNo = seatNo;
    }
}
