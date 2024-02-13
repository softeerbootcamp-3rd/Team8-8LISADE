package com.lisade.togeduck.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lisade.togeduck.dto.response.RouteDetailDao;
import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.exception.FestivalNotIncludeRouteException;
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

        RouteDetailDao routeDetailDao = createDummyRouteDetailDao(festivalId, routeId);
        when(routeRepository.findRouteDetail(anyLong())).thenReturn(Optional.of(routeDetailDao));

        // when
        RouteDetailDto routeDetailDto = routeService.getDetail(festivalId, routeId);

        // then
        assertThat(routeDetailDto).isNotNull();
        verify(routeRepository, times(1)).findRouteDetail(routeId);
        
        LocalTime expectedAt = routeDetailDao.getExpectedAt();
        assertThat(routeDetailDto.getId()).isEqualTo(routeDetailDao.getId());
        assertThat(routeDetailDto.getStartedAt()).isEqualTo(routeDetailDao.getStartedAt());
        assertThat(routeDetailDto.getSource()).isEqualTo(routeDetailDao.getSource());
        assertThat(routeDetailDto.getDestination()).isEqualTo(routeDetailDao.getDestination());
        assertThat(routeDetailDto.getExpectedAt()).isEqualTo(routeDetailDao.getExpectedAt());
        assertThat(routeDetailDto.getArrivalAt()).isEqualTo(
            routeDetailDao.getStartedAt().toLocalTime().plusHours(expectedAt.getHour())
                .plusMinutes(expectedAt.getMinute()).plusSeconds(expectedAt.getSecond()));
        assertThat(routeDetailDto.getTotalSeats()).isEqualTo(routeDetailDao.getTotalSeats());
        assertThat(routeDetailDto.getReservedSeats()).isEqualTo(routeDetailDao.getReservedSeats());
        assertThat(routeDetailDto.getCost()).isEqualTo(routeDetailDao.getCost());
    }

    @Test
    @DisplayName("사용자가 존재하지 않는 노선을 전달했을 경우 NotFoundException를 던짐")
    void requestUnValidCategoryOrRouteIdThrowNotFound() {
        // given
        Long festivalId = 1L;
        Long routeId = 100L;

        when(routeRepository.findRouteDetail(anyLong())).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> routeService.getDetail(festivalId, routeId))
            .isInstanceOf(NotFoundException.class);

        verify(routeRepository, times(1)).findRouteDetail(routeId);
    }

    @Test
    @DisplayName("사용자가 해당 행사에 대한 RouteId가 아닌, 다른 행사의 RouteId를 입력했을 시 FestivalNotIncludeRouteException을 던짐")
    void testGetDetailWithInvalidFestivalId() {
        // given
        Long festivalId = 1L;
        Long routeId = 100L;
        RouteDetailDao routeDetailDao = createDummyRouteDetailDao(2L, routeId);

        when(routeRepository.findRouteDetail(anyLong())).thenReturn(Optional.of(routeDetailDao));

        // when & then
        assertThatThrownBy(() -> routeService.getDetail(festivalId, routeId))
            .isInstanceOf(FestivalNotIncludeRouteException.class);

        verify(routeRepository, times(1)).findRouteDetail(routeId);
    }

    public RouteDetailDao createDummyRouteDetailDao(Long festivalId, Long routeId) {
        return RouteDetailDao.builder()
            .id(routeId)
            .festivalId(festivalId)
            .startedAt(LocalDateTime.now())
            .source("City A")
            .destination("City B")
            .expectedAt(LocalTime.of(3, 30))
            .totalSeats(50)
            .reservedSeats(20L)
            .cost(25)
            .build();
    }
}