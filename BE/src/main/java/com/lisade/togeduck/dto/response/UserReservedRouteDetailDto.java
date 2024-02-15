package com.lisade.togeduck.dto.response;

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
public class UserReservedRouteDetailDto {

    private Long festivalId;
    private String title;
    private Integer seatNo;
    private LocalTime expectedDuration;
    private String status;
    private Integer totalSeats;
    private Integer reservedSeats;
    private Integer price;
    private String imagePath;
    private LocalDateTime startedAt;
    private LocalTime arrivedAt;
    private String stationName;
    private String stationCity;
    private String festivalLocation;
    private String festivalCity;
    private DriverInfo driverInfo;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RouteAndFestivalInfo {

        private Long festivalId;
        private String title;
        private LocalTime expectedDuration;
        private String status;
        private Integer price;
        private LocalDateTime startedAt;
        private String festivalLocation;
        private String festivalCity;
        private String imagePath;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StationInfo {

        private String name;
        private String city;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DriverInfo {

        private Long id;
        private String name;
        private String company;
        private String phoneNumber;
        private String carNumber;
    }
}
