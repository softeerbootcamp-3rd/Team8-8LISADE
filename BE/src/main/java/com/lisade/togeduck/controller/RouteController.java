package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.request.RouteRegistrationRequest;
import com.lisade.togeduck.dto.response.RouteDetailResponse;
import com.lisade.togeduck.dto.response.RouteRegistrationResponse;
import com.lisade.togeduck.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/festivals/{festival_id}")
public class RouteController {

    private final RouteService routeService;

    @PostMapping("/routes")
    public RouteRegistrationResponse createRoute(
        @PathVariable("festival_id") Long festivalId, @RequestBody
    RouteRegistrationRequest routeRegistration) {
        return routeService.save(festivalId, routeRegistration);
    }

    @GetMapping("/routes/{route_id}")
    public RouteDetailResponse getDetail(@PathVariable("festival_id") Long festivalId,
        @PathVariable("route_id") Long routeId) {
        return routeService.getDetail(festivalId, routeId);
    }
}
