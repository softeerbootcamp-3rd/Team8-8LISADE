package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.request.SeatRegistrationRequest;
import com.lisade.togeduck.dto.response.SeatResponse;
import com.lisade.togeduck.entity.Seat;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.exception.SeatAlreadyRegisterException;
import com.lisade.togeduck.exception.SeatNotFoundException;
import com.lisade.togeduck.repository.SeatRepository;
import jakarta.persistence.LockModeType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public List<SeatResponse> getList(Long routeId) {
        List<SeatResponse> seats = seatRepository.findSeatsByRouteId(routeId);

        validateSeats(seats);

        return seats;
    }

    @Transactional
    public void saveAll(List<Seat> seats) {
        seatRepository.saveAll(seats);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Long register(User user, Long routeId,
        SeatRegistrationRequest seatRegistrationRequest) {
        Seat seat = get(routeId, seatRegistrationRequest.getNo());
        seat.getRoute().increaseNumberOfReservationSeats();

        validateSeat(seat);
        seat.reservation(user);
        return seat.getId();
    }

    public Seat get(Long routeId, Integer no) {
        return seatRepository.findByRouteIdAndNoWithRoute(routeId, no).orElseThrow(
            SeatNotFoundException::new);
    }

    private void validateSeat(Seat seat) {
        if (seat.getStatus().equals(SeatStatus.RESERVATION)) {
            throw new SeatAlreadyRegisterException();
        }
    }

    private void validateSeats(List<SeatResponse> seats) {
        if (seats.isEmpty()) {
            throw new RouteNotFoundException();
        }
    }
}
