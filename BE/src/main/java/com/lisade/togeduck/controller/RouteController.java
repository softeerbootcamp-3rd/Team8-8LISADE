package com.lisade.togeduck.controller;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.dto.request.RouteRegistrationRequest;
import com.lisade.togeduck.dto.response.CoordinateResponse;
import com.lisade.togeduck.dto.response.PaymentPageResponse;
import com.lisade.togeduck.dto.response.RouteDetailResponse;
import com.lisade.togeduck.dto.response.RouteRegistrationResponse;
import com.lisade.togeduck.dto.response.SeatListResponse;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.service.ChatRoomService;
import com.lisade.togeduck.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final ChatRoomService chatRoomService;


    @PostMapping("/festivals/{festival_id}/routes")
    public RouteRegistrationResponse createRoute(@Login User user,
        @PathVariable("festival_id") Long festivalId,
        @RequestBody
        RouteRegistrationRequest routeRegistration) {
        RouteRegistrationResponse registrationResponse = routeService.save(festivalId,
            routeRegistration);

        chatRoomService.create(registrationResponse.getRouteId()); // 채팅방 생성

        return registrationResponse;
    }

    @GetMapping("/festivals/{festival_id}/routes/{route_id}")
    public RouteDetailResponse getDetail(@PathVariable("festival_id") Long festivalId,
        @PathVariable("route_id") Long routeId) {
        return routeService.getDetail(festivalId, routeId);
    }

    @GetMapping("/routes/{route_id}/seats")
    public SeatListResponse getList(
        @PathVariable(name = "route_id") Long routeId) {
        return routeService.getSeats(routeId);
    }

    @GetMapping("/routes/{route_id}/coordinate")
    public CoordinateResponse getCoordinate(
        @PathVariable(name = "route_id") Long routeId
    ) {
        return routeService.getCoordinate(routeId);
    }

    @GetMapping("/routes/{route_id}/payment")
    public PaymentPageResponse getPaymentPage(@Login User user,
        @PathVariable(name = "route_id") Long routeId) {
        return routeService.getPaymentInfo(user.getId(), routeId);
    }
}
