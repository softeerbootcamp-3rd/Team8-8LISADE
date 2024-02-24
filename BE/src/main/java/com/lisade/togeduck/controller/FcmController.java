package com.lisade.togeduck.controller;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.dto.request.FcmTokenRequest;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FcmController {

    private final FcmService fcmService;

    @PostMapping
    public String saveToken(@Login User user, @RequestBody FcmTokenRequest fcmTokenRequest) {
        fcmService.save(user, fcmTokenRequest);

        return "정상적으로 저장되었습니다.";
    }
}
