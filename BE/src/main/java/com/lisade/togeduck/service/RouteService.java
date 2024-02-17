package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.request.RouteRegistrationRequest;
import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.dto.response.RouteDetailResponse;
import com.lisade.togeduck.dto.response.RouteRegistrationResponse;
import com.lisade.togeduck.entity.Bus;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.PriceTable;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Station;
import com.lisade.togeduck.exception.NotFoundException;
import com.lisade.togeduck.exception.RouteAlreadyExistsException;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.mapper.RouteMapper;
import com.lisade.togeduck.mapper.SeatMapper;
import com.lisade.togeduck.repository.RouteRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        if (exists(festivalId, routeRegistration.getStationId())) {
            throw new RouteAlreadyExistsException();
        }

        Festival festival = festivalService.get(festivalId);
        Station station = locationService.getStation(routeRegistration.getStationId());
        Bus bus = busService.get(routeRegistration.getBusId(), routeRegistration.getDistance());
        PriceTable priceTable = bus.getPriceTables().get(0);

        Integer numberOfSeats = bus.getNumberOfSeats();
        Integer price = priceTable.getPrice() / numberOfSeats / 100 * 100;

        Route route = routeRepository.save(RouteMapper.toRoute(festival, bus, station,
            routeRegistration, price));

        seatService.saveAll(SeatMapper.toSeats(route, bus.getNumberOfSeats()));

        return RouteMapper.toRouteRegistrationDto(route);
    }

    public Route get(Long routeId) {
        return routeRepository.findById(routeId).orElseThrow(RouteNotFoundException::new);
    }

    public boolean exists(Long festivalId, Long stationId) {
        return routeRepository.existsByFestivalIdAndStationId(festivalId, stationId);
    }

    public RouteDetailResponse getDetail(Long festivalId, Long routeId) {
        Optional<RouteDetailDto> optionalRouteDetailDto = routeRepository.findRouteDetail(routeId,
            festivalId);
        RouteDetailDto routeDetailDto = validateRouteDetailDto(optionalRouteDetailDto);
        LocalDateTime startedAt = routeDetailDto.getStartedAt();
        LocalTime expectedAt = routeDetailDto.getExpectedAt();

        LocalTime arrivalAt = startedAt.toLocalTime().plusHours(expectedAt.getHour())
            .plusMinutes(expectedAt.getMinute()).plusSeconds(expectedAt.getSecond());

        return RouteMapper.toRouteDetailResponse(routeDetailDto, arrivalAt);
    }

    private RouteDetailDto validateRouteDetailDto(
        Optional<RouteDetailDto> optionalRouteDetailDto) {
        if (optionalRouteDetailDto.isEmpty()) {
            log.error("NotFound 에러 발생");
            throw new NotFoundException();
        }
        return optionalRouteDetailDto.get();
    }
}
