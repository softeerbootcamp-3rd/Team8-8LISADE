package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.request.LoginDto;
import com.lisade.togeduck.dto.request.SignUpDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.DriverInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.RouteAndFestivalInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailDto.StationInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDto;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.exception.EmailAlreadyExistsException;
import com.lisade.togeduck.exception.NicknameAlreadyExistsException;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.exception.UserIdAlreadyExistsException;
import com.lisade.togeduck.exception.UserNotFoundException;
import com.lisade.togeduck.global.response.ApiResponse;
import com.lisade.togeduck.mapper.UserMapper;
import com.lisade.togeduck.repository.RouteRepository;
import com.lisade.togeduck.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RouteRepository routeRepository;

    @Transactional
    public Long join(SignUpDto signUpDto) {
        validateByUserId(signUpDto.getUserId());
        validateByNickname(signUpDto.getNickname());
        validateByEmail(signUpDto.getEmail());

        User user = UserMapper.toUser(signUpDto);
        User saveUser = userRepository.save(user);

        return saveUser.getId();
    }

    @Transactional
    public User login(LoginDto loginDto) {
        return userRepository.findByUserIdAndPassword(loginDto.getUserId(),
                loginDto.getPassword())
            .orElseThrow(UserNotFoundException::new);
    }

    public Slice<UserReservedRouteDto> getReservedRouteList(Pageable pageable, Long userId) {
        return routeRepository.findReservedRoutes(pageable, userId);
    }

    public ResponseEntity<Object> checkUserId(String userId) {
        validateByUserId(userId);

        return ResponseEntity.ok(
            ApiResponse.onSuccess(UserMapper.toValidateUserIdDto("사용가능한 아이디입니다.")));
    }

    public Slice<UserReservedRouteDto> getReservedRouteList(Pageable pageable, Long userId) {
        return routeRepository.findReservedRoutes(pageable, userId);
    }

    @Transactional(readOnly = true)
    public Optional<UserReservedRouteDetailDto> getReservedRouteInfo(Long userId, Long routeId) {
        RouteAndFestivalInfo routeAndFestivalInfo = routeRepository.findRouteAndFestivalInfo(
            routeId).orElseThrow(RouteNotFoundException::new);
        StationInfo stationInfo = routeRepository.findStationInfo(routeId)
            .orElseThrow(RouteNotFoundException::new);
        DriverInfo driverInfo = routeRepository.findDriverInfo(routeId)
            .orElseThrow(RouteNotFoundException::new);
        return Optional.empty();
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
}
