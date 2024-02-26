package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.request.RouteRegistrationRequest;
import com.lisade.togeduck.dto.response.BusLayoutResponse;
import com.lisade.togeduck.dto.response.CoordinateResponse;
import com.lisade.togeduck.dto.response.RouteCityAndDestinationDetail;
import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.dto.response.RouteDetailResponse;
import com.lisade.togeduck.dto.response.RouteRegistrationResponse;
import com.lisade.togeduck.dto.response.SeatListResponse;
import com.lisade.togeduck.dto.response.SeatResponse;
import com.lisade.togeduck.dto.response.UserSeatDetailResponse;
import com.lisade.togeduck.entity.Bus;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.PriceTable;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Station;
import com.lisade.togeduck.exception.FestivalNotFoundException;
import com.lisade.togeduck.exception.RouteAlreadyExistsException;
import com.lisade.togeduck.mapper.RouteMapper;
import com.lisade.togeduck.mapper.SeatMapper;
import com.lisade.togeduck.repository.RouteRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {

    private final BusService busService;
    private final SeatService seatService;
    private final FestivalService festivalService;
    private final LocationService locationService;
    private final RouteRepository routeRepository;

    @Transactional
    public RouteRegistrationResponse save(Long festivalId,
        RouteRegistrationRequest routeRegistration) {
        validationExists(festivalId, routeRegistration.getStationId());

        Festival festival = festivalService.get(festivalId);
        Station station = locationService.getStation(routeRegistration.getStationId());
        Bus bus = busService.get(routeRegistration.getBusId(), routeRegistration.getDistance());
        PriceTable priceTable = bus.getPriceTables().get(0);

        Integer numberOfSeats = bus.getNumberOfSeats();
        Integer price = priceTable.getPrice() / numberOfSeats / 100 * 100;

        Route route = routeRepository.save(RouteMapper.toRoute(festival, bus, station,
            routeRegistration, price));

        seatService.saveAll(SeatMapper.toSeats(route, bus.getNumberOfSeats()));

        return RouteMapper.toRouteRegistrationResponse(route);
    }

    public RouteDetailResponse getDetail(Long festivalId, Long routeId) {
        Optional<RouteDetailDto> optionalRouteDetailDto = routeRepository.findRouteDetail(routeId,
            festivalId);
        RouteDetailDto routeDetailDto = validateRouteDetailDto(optionalRouteDetailDto);

        LocalTime arrivalAt = getArrivalAt(routeDetailDto);

        return RouteMapper.toRouteDetailResponse(routeDetailDto, arrivalAt);
    }

    private LocalTime getArrivalAt(RouteDetailDto routeDetailDto) {
        LocalDateTime startedAt = routeDetailDto.getStartedAt();
        LocalTime expectedAt = routeDetailDto.getExpectedAt();
        return startedAt.toLocalTime().plusHours(expectedAt.getHour())
            .plusMinutes(expectedAt.getMinute()).plusSeconds(expectedAt.getSecond());
    }

    private void validationExists(Long festivalId, Long stationId) {
        if (routeRepository.existsByFestivalIdAndStationId(festivalId, stationId)) {
            throw new RouteAlreadyExistsException();
        }
    }

    private RouteDetailDto validateRouteDetailDto(
        Optional<RouteDetailDto> optionalRouteDetailDto) {
        return optionalRouteDetailDto.orElseThrow(FestivalNotFoundException::new);
    }

    @Transactional
    public SeatListResponse getSeats(Long routeId) {
        BusLayoutResponse busLayout = busService.getBusLayout(routeId);
        List<SeatResponse> seats = seatService.getList(routeId);
        RouteCityAndDestinationDetail routeCityAndDestinationDetail = routeRepository.getRouteDetail(
            routeId);
        return SeatMapper.toSeatListResponse(busLayout, routeCityAndDestinationDetail, seats);
    }

    public UserSeatDetailResponse getSeat(Long userId, Long routeId) {
        return routeRepository.getSeatDetail(userId, routeId);
    }

    public CoordinateResponse getCoordinate(Long routeId) {
        return routeRepository.getCoordinate(routeId);
    }
}
