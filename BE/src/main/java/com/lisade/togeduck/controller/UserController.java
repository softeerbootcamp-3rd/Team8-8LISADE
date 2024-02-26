package com.lisade.togeduck.controller;

import static com.lisade.togeduck.constant.SessionConst.LOGIN_USER;
import static org.springframework.http.HttpStatus.CREATED;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.dto.request.LoginRequest;
import com.lisade.togeduck.dto.request.SignUpRequest;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse;
import com.lisade.togeduck.dto.response.UserReservedRouteResponse;
import com.lisade.togeduck.dto.response.UserSeatDetailResponse;
import com.lisade.togeduck.dto.response.UserTicketResponse;
import com.lisade.togeduck.dto.response.ValidateUserIdResponse;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{user_id}")
    public ValidateUserIdResponse checkUserId(@PathVariable(name = "user_id") String userId) {
        return userService.checkUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Long> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        Long id = userService.join(signUpRequest);
        return new ResponseEntity<>(id, CREATED);
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequest loginRequest,
        HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loginUser = userService.login(loginRequest, session.getId());
        session.setAttribute(LOGIN_USER.getSessionName(), loginUser);
        return loginUser.getUserId();
    }

    @GetMapping("/routes")
    public Slice<UserReservedRouteResponse> getRoutes(
        @Login User user,
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {

        return userService.getReservedRouteList(pageable, user.getId());
    }

    @GetMapping("/routes/{route_id}")
    public UserReservedRouteDetailResponse getRouteInfo(@Login User user,
        @PathVariable(name = "route_id") Long routeId) {

        return userService.getReservedRouteInfo(user.getId(), routeId)
            .orElseThrow(RuntimeException::new);
    }

    @GetMapping("/routes/{route_id}/tickets")
    public UserTicketResponse getTicket(@Login User user,
        @PathVariable(name = "route_id") Long routeId) {
        return userService.getTicket(user.getId(), routeId);
    }

    @GetMapping("/routes/{route_id}/tickets/seats")
    public UserSeatDetailResponse getSeat(@Login User user,
        @PathVariable(name = "route_id") Long routeId) {
        return userService.getReservedSeat(user.getId(), routeId);
    }
}
