package com.lisade.togeduck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.lisade.togeduck.dto.request.SeatRegistrationRequest;
import com.lisade.togeduck.dto.response.BusLayoutResponse;
import com.lisade.togeduck.dto.response.SeatResponse;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Seat;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.exception.SeatAlreadyRegisterException;
import com.lisade.togeduck.exception.SeatNotFoundException;
import com.lisade.togeduck.repository.SeatRepository;
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
    private BusService busService;

    @Test
    @DisplayName("특정 노선에 대한 좌석 상태 조회 성공 테스트")
    void getSeatsOfRouteTest() {
        // given
        List<SeatResponse> response = seatsResponse();
        BusLayoutResponse busLayoutResponse = BusLayoutResponse.builder().row(3).col(4).backSeats(4)
            .numberOfSeats(16).build();

        doReturn(response)
            .when(seatRepository).findSeatsByRouteId(any(Long.class));
        doReturn(busLayoutResponse).when(busService).getBusLayout(anyLong());

        // when
        List<SeatResponse> list = seatService.getList(1L);

        // then
        assertEquals(16, list.size());

        verify(seatRepository, times(1)).findSeatsByRouteId(any(Long.class));
    }

    @Test
    @DisplayName("존재하지 않는 Route가 주어질 때 좌석 조회 실패 테스트")
    void getSeatsOfRouteWithNonExistRouteTest() {
        // given
        List<SeatResponse> response = new ArrayList<>();

        doReturn(response)
            .when(seatRepository).findSeatsByRouteId(any(Long.class));

        // when & then
        assertThrows(RouteNotFoundException.class, () -> {
            seatService.getList(1L);
        });
    }

    @Test
    @DisplayName("좌석 등록 성공 테스트")
    void registerSeatTest() {
        // given
        Integer no = 1;
        Long routeId = 1L;

        SeatRegistrationRequest request = SeatRegistrationRequest.builder()
            .no(no)
            .build();

        User user = User.builder()
            .userId("userId")
            .build();

        Route mockRoute = mock(Route.class);
        Seat seat = Seat.builder()
            .route(mockRoute)
            .no(no)
            .status(SeatStatus.AVAILABLE)
            .build();

        doReturn(Optional.of(seat)).when(seatRepository)
            .findByRouteIdAndNoWithRoute(routeId, no);
        // when
        seatService.register(user, routeId, request);

        // then
        verify(seatRepository, times(1))
            .findByRouteIdAndNoWithRoute(any(Long.class), any(Integer.class));
    }

    @Test
    @DisplayName("존재하지 않는 Seat가 주어졌을 때 좌석 등록 실패 테스트")
    void registerSeatWithNonExistsSeatTest() {
        // given
        Long routeId = 1L;
        Integer no = 1;

        User user = User.builder()
            .userId("userId")
            .build();

        SeatRegistrationRequest request = SeatRegistrationRequest.builder()
            .no(no)
            .build();

        doThrow(SeatNotFoundException.class).when(seatRepository)
            .findByRouteIdAndNoWithRoute(routeId, no);

        // when & then
        assertThrows(SeatNotFoundException.class, () -> {
            seatService.register(user, routeId, request);
        });
    }

    @Test
    @DisplayName("해당 Seat가 이미 예약 상태일 때 좌석 등록 실패 테스트")
    void registerReservationSeatTest() {
        // given
        Long routeId = 1L;
        Integer no = 1;

        SeatRegistrationRequest request = SeatRegistrationRequest.builder()
            .no(no)
            .build();

        User user = User.builder()
            .userId("userId")
            .build();
        Route mockRoute = mock(Route.class);
        Seat seat = Seat.builder()
            .route(mockRoute)
            .no(no)
            .status(SeatStatus.RESERVATION)
            .build();

        doReturn(Optional.of(seat)).when(seatRepository)
            .findByRouteIdAndNoWithRoute(routeId, no);

        // when
        assertThrows(SeatAlreadyRegisterException.class, () -> {
            seatService.register(user, routeId, request);
        });
    }

    private Route route() {
        Festival festival = Festival.builder()
            .id(1L)
            .build();

        return Route.builder()
            .festival(festival)
            .build();
    }

    private List<SeatResponse> seatsResponse() {
        List<SeatResponse> seats = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            seats.add(SeatResponse.builder()
                .id((long) i)
                .seatNo(i + 1)
                .status(SeatStatus.AVAILABLE)
                .build());
        }

        return seats;
    }
}
