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
public class UserReservationDetailDto {

    private Integer festivalId;
    private String title;
    private Integer seatNo;
    private LocalTime expectedDuration;
    private Integer totalSeats;
    private Integer reservedSeats;
    private Integer price;
    private Integer imagePath;
    private LocalDateTime startedAt;
    private LocalTime arrivedAt;
    private String stationName;
    private String festivalLocation;

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
