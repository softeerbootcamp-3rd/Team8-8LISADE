package com.lisade.togeduck.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReservedRouteDto {

    private Long id;
    private String title;
    private LocalDateTime startedAt;
    private String location;
    private String stationName;
    private Integer price;
    private String status;
    private Integer totalSeats;
    private Integer reservedSeats;
    private String imagePath;
}
