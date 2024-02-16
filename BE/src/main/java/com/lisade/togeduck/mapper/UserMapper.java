package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.dto.response.LoginEmptyFieldDto;
import com.lisade.togeduck.dto.response.SignUpFailureDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.BusInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.DriverInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.RouteAndFestivalInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.SeatInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.StationInfo;
import com.lisade.togeduck.dto.response.ValidateUserIdDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.enums.AuthorityType;
import java.time.LocalTime;
import java.util.Map;

public class UserMapper {

    public static SignUpFailureDto toSignUpFailureDto(Map<String, String> validationResult) {
        return SignUpFailureDto.builder()
            .userId(validationResult.get("userId"))
            .password(validationResult.get("password"))
            .nickname(validationResult.get("nickname"))
            .email(validationResult.get("email"))
            .build();
    }

    public static LoginEmptyFieldDto toLoginEmptyFieldDto(Map<String, String> validationResult) {
        return LoginEmptyFieldDto.builder()
            .userId(validationResult.get("userId"))
            .password(validationResult.get("password"))
            .build();
    }

    public static User toUser(SignUpDto signUpDto) {
        return User.builder()
            .userId(signUpDto.getUserId())
            .password(signUpDto.getPassword())
            .nickname(signUpDto.getNickname())
            .email(signUpDto.getEmail())
            .authority(AuthorityType.ROLE_USER)
            .build();
    }

    public static ValidateUserIdDto toValidateUserIdDto(String userIdMessage) {
        return ValidateUserIdDto.builder()
            .userId(userIdMessage)
            .build();
    }

    public static UserReservedRouteDetailDto toUserReservedRouteDetailDto(
        RouteAndFestivalInfo routeAndFestivalInfo, StationInfo stationInfo, SeatInfo seatInfo,
        BusInfo busInfo, DriverInfo driverInfo, LocalTime arrivedAt) {
        return UserReservedRouteDetailDto.builder()
            .festivalId(routeAndFestivalInfo.getFestivalId())
            .title(routeAndFestivalInfo.getTitle())
            .seatNo(seatInfo.getSeatNo())
            .expectedDuration(routeAndFestivalInfo.getExpectedDuration())
            .status(routeAndFestivalInfo.getStatus())
            .totalSeats(busInfo.getTotalSeats())
            .reservedSeats(seatInfo.getReservedSeats())
            .price(routeAndFestivalInfo.getPrice())
            .imagePath(routeAndFestivalInfo.getImagePath())
            .startedAt(routeAndFestivalInfo.getStartedAt())
            .arrivedAt(arrivedAt)
            .stationName(stationInfo.getName())
            .stationCity(stationInfo.getCity())
            .festivalLocation(routeAndFestivalInfo.getFestivalLocation())
            .festivalCity(routeAndFestivalInfo.getFestivalCity())
            .driverInfo(driverInfo)
            .build();
    }
}
