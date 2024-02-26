package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.CoordinateResponse;
import com.lisade.togeduck.dto.response.FestivalRoutesResponse;
import com.lisade.togeduck.dto.response.RouteCityAndDestinationDetail;
import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.BusInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.DriverInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.RouteAndFestivalInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.SeatInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.StationInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteResponse;
import com.lisade.togeduck.dto.response.UserSeatDetailResponse;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RouteRepositoryCustom {


    Optional<RouteDetailDto> findRouteDetail(Long routeId, Long festivalId);

    Slice<UserReservedRouteResponse> findReservedRoutes(Pageable pageable, Long userId);

    Optional<RouteAndFestivalInfo> findRouteAndFestivalInfo(Long routeId);

    Optional<StationInfo> findStationInfo(Long routeId);

    Optional<DriverInfo> findDriverInfo(Long routeId);

    Optional<BusInfo> findBusInfo(Long routeId);

    Optional<SeatInfo> findSeatInfo(Long routeId, Long userId);

    Slice<FestivalRoutesResponse> findRoutes(Pageable pageable, Long festivalId, String cityName);

    RouteCityAndDestinationDetail getRouteDetail(Long routeId);

    UserSeatDetailResponse getSeatDetail(Long userId, Long routeId);

    CoordinateResponse getCoordinate(Long routeId);
}
