package com.lisade.togeduck.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lisade.togeduck.dto.response.RouteDetailDto;
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

    @Test
    @DisplayName("사용자가 특적 페스티벌 id와 노선 id를 전달하였을때 해당 노선에 대한 자세한 정보를 return 한다.")
    void getDetail() throws Exception {
        //given
        RouteDetailDto mockResponse = RouteDetailDto.builder()
            .id(1L)
            .startedAt(LocalDateTime.now())
            .source("Source")
            .destination("Destination")
            .departureAt(LocalTime.of(6, 24))
            .arrivalAt(LocalTime.of(10, 24))
            .totalSeats(100L)
            .reservedSeats(20L)
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
        result.andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.source").value("Source"))
            .andExpect(jsonPath("$.destination").value("Destination"))
            .andExpect(jsonPath("$.totalSeats").value(100))
            .andExpect(jsonPath("$.reservedSeats").value(20))
            .andExpect(jsonPath("$.cost").value(50));
    }
}
