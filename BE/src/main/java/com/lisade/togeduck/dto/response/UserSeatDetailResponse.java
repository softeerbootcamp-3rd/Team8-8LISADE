package com.lisade.togeduck.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSeatDetailResponse {

    Integer seatNo;
    Integer totalSeats;
    Integer row;
    Integer column;
    Integer backSeat;
}
