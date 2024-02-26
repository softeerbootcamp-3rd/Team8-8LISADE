package com.lisade.togeduck.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RouteCityAndDestinationDetail {

    Integer reservedSeats;
    String sourceCity;
    String source;
    String destinationCity;
    String destination;
    Integer price;
}
