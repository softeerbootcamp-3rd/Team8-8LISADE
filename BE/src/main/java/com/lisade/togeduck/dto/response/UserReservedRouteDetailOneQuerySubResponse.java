package com.lisade.togeduck.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lisade.togeduck.entity.enums.RouteStatus;
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
public class UserReservedRouteDetailOneQuerySubResponse {

    private Long festivalId;
    private String title;
    private Integer seatNo;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime expectedDuration;
    private RouteStatus status;
    private Integer totalSeats;
    private Integer reservedSeats;
    private Integer price;
    private String imagePath;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime startedAt;
    private String stationName;
    private String stationCity;
    private String festivalLocation;
    private String festivalCity;
    private Long driverId;
    private String name;
    private String company;
    private String phoneNumber;
    private String carNumber;
    private String driverImagePath;
}
