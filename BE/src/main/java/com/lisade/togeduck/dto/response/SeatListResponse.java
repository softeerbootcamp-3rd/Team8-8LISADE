package com.lisade.togeduck.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatListResponse {

    private Integer totalSeats;
    private Integer row;
    private Integer col;
    private Integer backSeats;
    private Integer reservedSeats;
    private String sourceCity;
    private String source;
    private String destinationCity;
    private String destination;
    private Integer price;
    private List<SeatResponse> seats;
}
