package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.request.SeatRegistrationDto;
import com.lisade.togeduck.dto.response.BusLayoutDto;
import com.lisade.togeduck.dto.response.SeatDto;
import com.lisade.togeduck.dto.response.SeatListDto;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Seat;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.UserRoute;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.lisade.togeduck.exception.FestivalNotIncludeRouteException;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.exception.SeatAlreadyRegisterException;
import com.lisade.togeduck.exception.SeatNotFoundException;
import com.lisade.togeduck.mapper.SeatMapper;
import com.lisade.togeduck.repository.SeatRepository;
import com.lisade.togeduck.repository.UserRouteRepository;
import jakarta.persistence.LockModeType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final UserRouteRepository userRouteRepository;
    private final SeatRepository seatRepository;
    private final BusService busService;

    public SeatListDto getList(Long festivalId, Long routeId) {
        List<Seat> seats = seatRepository.findAllByRouteId(routeId);

        validateSeats(seats);
        validateRoute(seats.get(0).getRoute(), festivalId);
        
        return SeatMapper.toSeatListDto(seats);
    }

    @Transactional
    public void saveAll(List<Seat> seats) {
        seatRepository.saveAll(seats);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Long register(User user, Long festivalId, Long routeId,
        SeatRegistrationDto seatRegistration) {
        Seat seat = get(routeId, seatRegistration.getNo());
        Route route = seat.getRoute();

        validateSeat(seat);
        validateRoute(route, festivalId);

        seat.setStatus(SeatStatus.RESERVATION);

        UserRoute userRoute = UserRoute.builder()
            .user(user)
            .seat(seat)
            .route(route)
            .build();

        return userRouteRepository.save(userRoute).getId();
    }

    public Seat get(Long routeId, Integer no) {
        return seatRepository.findByRouteIdAndNo(routeId, no).orElseThrow(
            SeatNotFoundException::new);
    }

    private void validateRoute(Route route, Long festivalId) {
        if (!route.getFestival().getId().equals(festivalId)) {
            throw new FestivalNotIncludeRouteException();
        }
    }

    private void validateSeat(Seat seat) {
        if (seat.getStatus().equals(SeatStatus.RESERVATION)) {
            throw new SeatAlreadyRegisterException();
        }
    }

    private void validateSeats(List<Seat> seats) {
        if (seats.isEmpty()) {
            throw new RouteNotFoundException();
        }
    }
}
