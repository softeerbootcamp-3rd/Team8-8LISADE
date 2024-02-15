package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.BusLayoutDto;
import com.lisade.togeduck.dto.response.SeatDto;
import java.util.List;

public interface SeatRepositoryCustom {

    List<SeatDto> findSeatsByRouteId(Long routeId);

    BusLayoutDto findBusLayoutByRouteId(Long routeId);
}
