package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.BusInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.DriverInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.RouteAndFestivalInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.StationInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDto;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RouteRepositoryCustom {


    Optional<RouteDetailDto> findRouteDetail(Long routeId, Long festivalId);

    Slice<UserReservedRouteDto> findReservedRoutes(Pageable pageable, Long userId);

    Optional<RouteAndFestivalInfo> findRouteAndFestivalInfo(Long routeId);

    Optional<StationInfo> findStationInfo(Long routeId);

    Optional<DriverInfo> findDriverInfo(Long routeId);

    Optional<BusInfo> findBusInfo(Long routeId);
}
