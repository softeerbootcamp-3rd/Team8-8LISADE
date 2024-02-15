package com.lisade.togeduck.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusLayoutDto {

    private Integer numberOfSeats;
    private Integer row;
    private Integer col;
    private Integer backSeats;
}
