package com.lisade.togeduck.service;

import com.lisade.togeduck.repository.CityRepository;
import com.lisade.togeduck.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

class LocationServiceTest {

    private LocationService locationService;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private StationRepository stationRepository;
    @Mock
    private FestivalService festivalService;
    @Mock
    private BusService busService;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        locationService = new LocationService(restTemplate, cityRepository, stationRepository,
            festivalService, busService);
    }

    @Test
    @DisplayName("TMap으로 요청을 했을 때 정상적으로 응답이 오는지 테스트")
    void getTMapResponseTest() {
        // given
        Long stationId = 1L;
        Long festivalId = 1L;

        // when
//        DistancePricesDto distance = locationService.getDistance(stationId, festivalId);

        // then
//        assertNotNull(distance);
    }
}