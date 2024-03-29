package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.response.DistancePricesResponse;
import com.lisade.togeduck.dto.response.LocationListDto;
import com.lisade.togeduck.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public LocationListDto getList() {
        return locationService.getList();
    }

    @GetMapping("/distance")
    public DistancePricesResponse getDistance(@RequestParam Long stationId,
        @RequestParam Long festivalId) {
        return locationService.getDistance(stationId, festivalId);
    }
}
