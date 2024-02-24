package com.lisade.togeduck.service;

import com.lisade.togeduck.cache.service.SessionCacheService;
import com.lisade.togeduck.dto.request.LoginRequest;
import com.lisade.togeduck.dto.request.SignUpRequest;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.BusInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.DriverInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.RouteAndFestivalInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.SeatInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.StationInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteResponse;
import com.lisade.togeduck.dto.response.ValidateUserIdResponse;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.exception.EmailAlreadyExistsException;
import com.lisade.togeduck.exception.NicknameAlreadyExistsException;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.exception.UserIdAlreadyExistsException;
import com.lisade.togeduck.exception.UserNotFoundException;
import com.lisade.togeduck.mapper.UserMapper;
import com.lisade.togeduck.repository.RouteRepository;
import com.lisade.togeduck.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final SessionCacheService sessionCacheService;

    public User get(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public Long join(SignUpRequest signUpRequest) {
        validateByUserId(signUpRequest.getUserId());
        validateByNickname(signUpRequest.getNickname());
        validateByEmail(signUpRequest.getEmail());

        User user = UserMapper.toUser(signUpRequest);
        User saveUser = userRepository.save(user);

        return saveUser.getId();
    }

    @Transactional
    public User login(LoginRequest loginRequest, String session) {
        User user = userRepository.findByUserIdAndPassword(loginRequest.getUserId(),
                loginRequest.getPassword())
            .orElseThrow(UserNotFoundException::new);

        sessionCacheService.save(user.getNickname(), session);

        return user;
    }

    @Transactional(readOnly = true)
    public Optional<UserReservedRouteDetailResponse> getReservedRouteInfo(Long userId,
        Long routeId) {
        RouteAndFestivalInfo routeAndFestivalInfo = findRouteAndFestivalInfo(routeId);
        StationInfo stationInfo = findStationInfo(routeId);
        SeatInfo seatInfo = findSeatInfo(routeId, userId);
        BusInfo busInfo = findBusInfo(routeId);
        DriverInfo driverInfo = findDriverInfo(routeId);
        LocalTime arrivedAt = getArrivedAt(routeAndFestivalInfo.getStartedAt(),
            routeAndFestivalInfo.getExpectedDuration());

        return Optional.ofNullable(
            UserMapper.toUserReservedRouteDetailResponse(routeAndFestivalInfo, stationInfo,
                seatInfo,
                busInfo, driverInfo, arrivedAt));
    }

    public Slice<UserReservedRouteResponse> getReservedRouteList(Pageable pageable, Long userId) {
        return routeRepository.findReservedRoutes(pageable, userId);
    }

    public ValidateUserIdResponse checkUserId(String userId) {
        validateByUserId(userId);

        return UserMapper.toValidateUserIdResponse("사용가능한 아이디입니다.");
    }

    private void validateByUserId(String userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new UserIdAlreadyExistsException();
        }
    }

    private void validateByNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new NicknameAlreadyExistsException();
        }
    }

    private void validateByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }
    }

    private RouteAndFestivalInfo findRouteAndFestivalInfo(Long routeId) {
        return routeRepository.findRouteAndFestivalInfo(routeId)
            .orElseThrow(RouteNotFoundException::new);
    }

    private StationInfo findStationInfo(Long routeId) {
        return routeRepository.findStationInfo(routeId)
            .orElseThrow(RouteNotFoundException::new);
    }

    private SeatInfo findSeatInfo(Long routeId, Long userId) {
        return routeRepository.findSeatInfo(routeId, userId)
            .orElseThrow(RouteNotFoundException::new);
    }

    private BusInfo findBusInfo(Long routeId) {
        return routeRepository.findBusInfo(routeId)
            .orElseThrow(RouteNotFoundException::new);
    }

    private DriverInfo findDriverInfo(Long routeId) {
        return routeRepository.findDriverInfo(routeId)
            .orElseThrow(RouteNotFoundException::new);
    }

    private LocalTime getArrivedAt(LocalDateTime startedAt, LocalTime expectedAt) {
        return startedAt.toLocalTime().plusHours(expectedAt.getHour())
            .plusMinutes(expectedAt.getMinute()).plusSeconds(expectedAt.getSecond());
    }
}
