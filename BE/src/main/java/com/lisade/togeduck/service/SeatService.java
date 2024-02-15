package com.lisade.togeduck.service;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.dto.request.SeatRegistrationDto;
import com.lisade.togeduck.dto.response.BusLayoutDto;
import com.lisade.togeduck.dto.response.SeatDto;
import com.lisade.togeduck.dto.response.SeatListDto;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Seat;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.UserRoute;
import com.lisade.togeduck.entity.enums.SeatStatus;
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

    @Transactional(readOnly = true)
    public SeatListDto getList(Long routeId) {
        BusLayoutDto busLayoutByRouteId = busService.getBusLayout(routeId);
        List<SeatDto> seats = seatRepository.findSeatsByRouteId(routeId);
        return SeatMapper.toSeatListDto(busLayoutByRouteId, seats);
    }

    @Transactional
    public void saveAll(List<Seat> seats) {
        seatRepository.saveAll(seats);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Long register(@Login User user, Long routeId, SeatRegistrationDto seatRegistration) {
        Seat seat = get(routeId, seatRegistration.getNo());
        Route route = seat.getRoute();

        if (seat.getStatus().equals(SeatStatus.RESERVATION)) {
            throw new SeatAlreadyRegisterException();
        }

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
}
