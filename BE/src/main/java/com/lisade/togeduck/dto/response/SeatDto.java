package com.lisade.togeduck.dto.response;

import com.lisade.togeduck.entity.enums.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatDto {

    private Long id;
    private Integer seatNo;
    private SeatStatus status;

}
