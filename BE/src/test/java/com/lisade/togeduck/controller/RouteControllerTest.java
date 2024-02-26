package com.lisade.togeduck.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lisade.togeduck.dto.response.RouteDetailResponse;
import com.lisade.togeduck.service.ChatRoomService;
import com.lisade.togeduck.service.RouteService;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(RouteController.class)
@MockBean(JpaMetamodelMappingContext.class)
class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    RouteService routeService;
    @MockBean
    ChatRoomService chatRoomService;

    @Test
    @DisplayName("사용자가 특적 페스티벌 id와 노선 id를 전달하였을때 해당 노선에 대한 자세한 정보를 return 한다.")
    void getDetail() throws Exception {
        //given
        LocalTime expectedAt = LocalTime.of(10, 24);
        LocalDateTime startedAt = LocalDateTime.now();
        RouteDetailResponse mockResponse = RouteDetailResponse.builder()
            .id(1L)
            .startedAt(startedAt)
            .source("Source")
            .destination("Destination")
            .expectedAt(expectedAt)
            .arrivalAt(startedAt.toLocalTime().plusHours(expectedAt.getHour())
                .plusMinutes(expectedAt.getMinute()).plusSeconds(expectedAt.getSecond()))
            .totalSeats(100)
            .reservedSeats(20)
            .cost(50)
            .build();

        //when
        when(routeService.getDetail(any(Long.class), any(Long.class))).thenReturn(mockResponse);

        ResultActions result = mockMvc.perform(
                get("/festivals/{festival_id}/routes/{route_id}", 1, 2)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        //then
        verify(routeService).getDetail(eq(1L), eq(2L));
        result.andExpect(jsonPath("$.result.id").value(1))
            .andExpect(jsonPath("$.result.source").value("Source"))
            .andExpect(jsonPath("$.result.destination").value("Destination"))
            .andExpect(jsonPath("$.result.totalSeats").value(100))
            .andExpect(jsonPath("$.result.reservedSeats").value(20))
            .andExpect(jsonPath("$.result.cost").value(50));
    }
}
