package com.lisade.togeduck.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.dto.response.RouteDetailResponse;
import com.lisade.togeduck.exception.NotFoundException;
import com.lisade.togeduck.repository.RouteRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteService routeService;

    @Test
    @DisplayName("사용자가 행사와 노선 정보를 보내면 이를 검증하고 노선 상세정보를 반환한다.")
    void getDetail() {
        //given
        Long festivalId = 1L;
        Long routeId = 100L;

        RouteDetailDto routeDetailDto = createDummyRouteDetailDto(festivalId, routeId);
        when(routeRepository.findRouteDetail(anyLong(), anyLong())).thenReturn(
            Optional.of(routeDetailDto));

        // when
        RouteDetailResponse routeDetailResponse = routeService.getDetail(festivalId, routeId);

        // then
        assertThat(routeDetailResponse).isNotNull();
        verify(routeRepository, times(1)).findRouteDetail(routeId, festivalId);

        LocalTime expectedAt = routeDetailDto.getExpectedAt();
        assertThat(routeDetailResponse.getId()).isEqualTo(routeDetailDto.getId());
        assertThat(routeDetailResponse.getStartedAt()).isEqualTo(routeDetailDto.getStartedAt());
        assertThat(routeDetailResponse.getSource()).isEqualTo(routeDetailDto.getSource());
        assertThat(routeDetailResponse.getDestination()).isEqualTo(routeDetailDto.getDestination());
        assertThat(routeDetailResponse.getExpectedAt()).isEqualTo(routeDetailDto.getExpectedAt());
        assertThat(routeDetailResponse.getArrivalAt()).isEqualTo(
            routeDetailDto.getStartedAt().toLocalTime().plusHours(expectedAt.getHour())
                .plusMinutes(expectedAt.getMinute()).plusSeconds(expectedAt.getSecond()));
        assertThat(routeDetailResponse.getTotalSeats()).isEqualTo(routeDetailDto.getTotalSeats());
        assertThat(routeDetailResponse.getReservedSeats()).isEqualTo(
            routeDetailDto.getReservedSeats());
        assertThat(routeDetailResponse.getCost()).isEqualTo(routeDetailDto.getCost());
    }

    @Test
    @DisplayName("사용자가 존재하지 않는 노선을 전달했을 경우 NotFoundException를 던짐")
    void requestUnValidCategoryOrRouteIdThrowNotFound() {
        // given
        Long festivalId = 1L;
        Long routeId = 100L;

        when(routeRepository.findRouteDetail(anyLong(), anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> routeService.getDetail(festivalId, routeId))
            .isInstanceOf(NotFoundException.class);

        verify(routeRepository, times(1)).findRouteDetail(routeId, festivalId);
    }

    @Test
    @DisplayName("사용자가 해당 행사에 대한 RouteId가 아닌, 다른 행사의 RouteId를 입력했을 시 FestivalNotIncludeRouteException을 던짐")
    void testGetDetailWithInvalidFestivalId() {
        // given
        Long festivalId = 1L;
        Long routeId = 100L;

        when(routeRepository.findRouteDetail(anyLong(), anyLong())).thenReturn(
            Optional.ofNullable(null));

        // when & then
        assertThatThrownBy(() -> routeService.getDetail(festivalId, routeId))
            .isInstanceOf(NotFoundException.class);

        verify(routeRepository, times(1)).findRouteDetail(routeId, festivalId);
    }

    public RouteDetailDto createDummyRouteDetailDto(Long festivalId, Long routeId) {
        return RouteDetailDto.builder()
            .id(routeId)
            .startedAt(LocalDateTime.now())
            .source("City A")
            .destination("City B")
            .expectedAt(LocalTime.of(3, 30))
            .totalSeats(50)
            .reservedSeats(20)
            .cost(25)
            .build();
    }
}
