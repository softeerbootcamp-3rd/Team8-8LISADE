package com.lisade.togeduck.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lisade.togeduck.dto.request.SeatRegistrationDto;
import com.lisade.togeduck.dto.response.SeatDto;
import com.lisade.togeduck.dto.response.SeatListDto;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.exception.SeatAlreadyRegisterException;
import com.lisade.togeduck.exception.SeatNotFoundException;
import com.lisade.togeduck.global.exception.Error;
import com.lisade.togeduck.global.exception.GeneralException;
import com.lisade.togeduck.global.handler.GlobalExceptionHandler;
import com.lisade.togeduck.global.response.ApiResponse;
import com.lisade.togeduck.service.SeatService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SeatController.class)
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
class SeatControllerTest {

    private SeatController seatController;
    @MockBean
    private SeatService seatService;
    @Mock
    private GlobalExceptionHandler globalExceptionHandler;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        seatController = new SeatController(seatService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(seatController)
            .setControllerAdvice(globalExceptionHandler)
            .build();
    }

    @Test
    @DisplayName("특정 노선에 대한 남은 좌석을 조회 성공 테스트")
    public void getSeatsOfRouteTest() throws Exception {
        // given
        Long festivalId = 1L;
        Long routeId = 1L;

        doReturn(seatsResponse()).when(seatService)
            .getList(any(Long.class));

        // when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/festivals/{festival_id}/routes/{route_id}/seats",
                festivalId, routeId)
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
            .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        SeatListDto response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            SeatListDto.class);

        assertEquals(5, response.getNumberOfSeats());
    }

    @Test
    @DisplayName("존재하지 않는 Route가 주어질 때 좌석 조회 실패 테스트")
    public void getSeatsOfRouteWithNonExistRouteTest() throws Exception {
        // given
        Long festivalId = 1L;
        Long routeId = 1L;

        doReturn(seatsResponse()).when(seatService)
            .getList(any(Long.class));

        // when & then
        RouteNotFoundException routeNotFoundException = new RouteNotFoundException();
        ResponseEntity<Object> response = getResponseEntity(routeNotFoundException);

        when(seatService.getList(any(Long.class)))
            .thenThrow(routeNotFoundException);

        when(globalExceptionHandler.handleGeneralException(any(GeneralException.class),
            any(WebRequest.class))).thenReturn(response);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/festivals/{festival_id}/routes/{route_id}/seats",
                        festivalId, routeId)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound())
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(
                RouteNotFoundException.class));
    }

    @Test
    @DisplayName("좌석 등록 성공 테스트")
    void registerSeatTest() throws Exception {
        // given
        Long festivalId = 1L;
        Long routeId = 1L;
        SeatRegistrationDto request = SeatRegistrationDto.builder()
            .no(1)
            .build();

        when(seatService.register(any(Long.class), any(SeatRegistrationDto.class)))
            .thenReturn(1L);

        // when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/festivals/{festival_id}/routes/{route_id}/seats",
                    festivalId, routeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
            .andReturn();
    }

    @Test
    @DisplayName("존재하지 않는 Seat가 주어졌을 때 좌석 등록 실패 테스트")
    void registerSeatWithNonExistsSeatTest() throws Exception {
        // given
        Long festivalId = 1L;
        Long routeId = 1L;
        SeatRegistrationDto request = SeatRegistrationDto.builder()
            .no(1)
            .build();

        SeatNotFoundException seatNotFoundException = new SeatNotFoundException();
        ResponseEntity<Object> response = getResponseEntity(seatNotFoundException);

        when(seatService.register(any(Long.class), any(SeatRegistrationDto.class)))
            .thenThrow(seatNotFoundException);

        when(globalExceptionHandler.handleGeneralException(any(GeneralException.class),
            any(WebRequest.class))).thenReturn(response);

        // when & then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/festivals/{festival_id}/routes/{route_id}/seats",
                        festivalId, routeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(request))
            )
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(
                SeatNotFoundException.class));

        verify(globalExceptionHandler, times(1)).handleGeneralException(any(), any());
        verify(seatService, times(1)).register(any(), any());
    }


    @Test
    @DisplayName("해당 Seat가 이미 예약 상태일 때 좌석 등록 실패 테스트")
    void registerReservationSeatTest() throws Exception {
        // given
        Long festivalId = -1L;
        Long routeId = 1L;
        SeatRegistrationDto request = SeatRegistrationDto.builder()
            .no(1)
            .build();

        SeatAlreadyRegisterException seatAlreadyRegisterException = new SeatAlreadyRegisterException();
        ResponseEntity<Object> response = getResponseEntity(seatAlreadyRegisterException);

        when(seatService.register(any(Long.class), any(SeatRegistrationDto.class)))
            .thenThrow(seatAlreadyRegisterException);

        when(globalExceptionHandler.handleGeneralException(any(GeneralException.class),
            any(WebRequest.class))).thenReturn(response);

        // when & then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/festivals/{festival_id}/routes/{route_id}/seats",
                        festivalId, routeId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(request))
            ).andExpect(status().isConflict())
            .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(
                SeatAlreadyRegisterException.class));
    }

    private SeatListDto seatsResponse() {
        List<SeatDto> seats = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            seats.add(SeatDto.builder()
                .id((long) i)
                .seatNo(i + 1)
                .status(SeatStatus.AVAILABLE.toString())
                .build());
        }

        return SeatListDto.builder()
            .numberOfSeats(seats.size())
            .seats(seats)
            .build();
    }

    private ResponseEntity<Object> getResponseEntity(GeneralException ex) {
        Error error = ex.getError();
        HttpStatus httpStatus = error.getHttpStatus();
        String message = error.getMessage();
        ApiResponse<Object> body = ApiResponse.of(httpStatus.value(), httpStatus.name(), message);
        HttpHeaders headers = HttpHeaders.EMPTY;
        WebRequest mockWebRequest = mock(WebRequest.class);
        return handleExceptionInternal(ex, body, headers, httpStatus, mockWebRequest);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
        HttpHeaders headers, HttpStatusCode
        statusCode, WebRequest request) {
        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletResponse response = servletWebRequest.getResponse();
            if (response != null && response.isCommitted()) {
                return null;
            }
        }
        if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR) && body == null) {
            request.setAttribute("jakarta.servlet.error.exception", ex, 0);
        }

        return this.createResponseEntity(body, headers, statusCode, request);
    }


    private ResponseEntity<Object> createResponseEntity(@Nullable Object body, HttpHeaders headers,
        HttpStatusCode statusCode, WebRequest request) {
        return new ResponseEntity<>(body, headers, statusCode);
    }
}
