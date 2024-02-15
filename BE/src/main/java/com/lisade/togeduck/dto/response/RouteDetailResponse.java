package com.lisade.togeduck.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteDetailResponse {

    Long id;
    LocalDateTime startedAt;
    String source;
    String destination;
    LocalTime expectedAt;
    LocalTime arrivalAt;
    Integer totalSeats;
    Integer reservedSeats;
    Integer cost;
}
