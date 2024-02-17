package com.lisade.togeduck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.lisade.togeduck.dto.request.SeatRegistrationDto;
import com.lisade.togeduck.dto.response.SeatListDto;
import com.lisade.togeduck.entity.Seat;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.UserRoute;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.exception.SeatAlreadyRegisterException;
import com.lisade.togeduck.exception.SeatNotFoundException;
import com.lisade.togeduck.repository.SeatRepository;
import com.lisade.togeduck.repository.UserRouteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @InjectMocks
    private SeatService seatService;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private UserRouteRepository userRouteRepository;

    @Test
    @DisplayName("특정 노선에 대한 좌석 상태 조회 성공 테스트")
    void getSeatsOfRouteTest() {
        // given
        List<Seat> response = seatsResponse();

        doReturn(response)
            .when(seatRepository).findAllByRouteId(any(Long.class));

        // when
        SeatListDto seatListDto = seatService.getList(1L, 1L);

        // then
        assertEquals(5, seatListDto.getNumberOfSeats());

        verify(seatRepository, times(1)).findAllByRouteId(any(Long.class));
    }

    @Test
    @DisplayName("존재하지 않는 Route가 주어질 때 좌석 조회 실패 테스트")
    void getSeatsOfRouteWithNonExistRouteTest() {
        // given
        List<Seat> response = new ArrayList<>();

        doReturn(response)
            .when(seatRepository).findAllByRouteId(any(Long.class));

        // when & then
        assertThrows(RouteNotFoundException.class, () -> {
            SeatListDto seatListDto = seatService.getList(1L, 1L);
        });
    }

    @Test
    @DisplayName("좌석 등록 성공 테스트")
    void registerSeatTest() {
        // given
        Long festivalId = 1L;
        Long routeId = 1L;
        Integer no = 1;

        SeatRegistrationDto request = SeatRegistrationDto.builder()
            .no(no)
            .build();

        User user = User.builder()
            .userId("userId")
            .build();

        Seat seat = Seat.builder()
            .no(no)
            .status(SeatStatus.AVAILABLE)
            .build();

        UserRoute userRoute = UserRoute.builder()
            .id(1L)
            .build();

        doReturn(Optional.of(seat)).when(seatRepository)
            .findByRouteIdAndNo(routeId, no);

        doReturn(userRoute).when(userRouteRepository)
            .save(any(UserRoute.class));

        // when
        seatService.register(user, festivalId, routeId, request);

        // then
        verify(seatRepository, times(1))
            .findByRouteIdAndNo(any(Long.class), any(Integer.class));
        verify(userRouteRepository, times(1))
            .save(any(UserRoute.class));
    }

    @Test
    @DisplayName("존재하지 않는 Seat가 주어졌을 때 좌석 등록 실패 테스트")
    void registerSeatWithNonExistsSeatTest() {
        // given
        Long festivalId = 1L;
        Long routeId = 1L;
        Integer no = 1;

        User user = User.builder()
            .userId("userId")
            .build();

        SeatRegistrationDto request = SeatRegistrationDto.builder()
            .no(no)
            .build();

        doThrow(SeatNotFoundException.class).when(seatRepository)
            .findByRouteIdAndNo(routeId, no);

        // when & then
        assertThrows(SeatNotFoundException.class, () -> {
            seatService.register(user, festivalId, routeId, request);
        });
    }

    @Test
    @DisplayName("해당 Seat가 이미 예약 상태일 때 좌석 등록 실패 테스트")
    void registerReservationSeatTest() {
        // given
        Long festivalId = 1L;
        Long routeId = 1L;
        Integer no = 1;

        SeatRegistrationDto request = SeatRegistrationDto.builder()
            .no(no)
            .build();

        User user = User.builder()
            .userId("userId")
            .build();

        Seat seat = Seat.builder()
            .no(no)
            .status(SeatStatus.RESERVATION)
            .build();

        doReturn(Optional.of(seat)).when(seatRepository)
            .findByRouteIdAndNo(routeId, no);

        // when
        assertThrows(SeatAlreadyRegisterException.class, () -> {
            seatService.register(user, festivalId, routeId, request);
        });
    }

    private List<Seat> seatsResponse() {
        List<Seat> seats = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            seats.add(Seat.builder()
                .id((long) i)
                .no(i + 1)
                .status(SeatStatus.AVAILABLE)
                .build());
        }

        return seats;
    }
}