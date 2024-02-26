package com.lisade.togeduck.controller;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.dto.request.SeatRegistrationRequest;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;


    @PostMapping
    public Long register(
        @Login User user,
        @PathVariable(name = "route_id") Long routeId,
        @RequestBody SeatRegistrationRequest seatRegistration) {
        return seatService.register(user, routeId, seatRegistration);
    }
}
