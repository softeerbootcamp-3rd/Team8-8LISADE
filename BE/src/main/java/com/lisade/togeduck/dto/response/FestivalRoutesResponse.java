package com.lisade.togeduck.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lisade.togeduck.entity.enums.RouteStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FestivalRoutesResponse {

    private Long id;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime startedAt;
    private String station;
    private Integer price;
    private RouteStatus status;
    private Integer totalSeats;
    private Integer reservedSeats;
}
