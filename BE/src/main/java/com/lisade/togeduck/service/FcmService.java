package com.lisade.togeduck.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.lisade.togeduck.cache.service.FcmTokenCacheService;
import com.lisade.togeduck.dto.request.FcmTokenRequest;
import com.lisade.togeduck.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;
    private final FcmTokenCacheService fcmTokenCacheService;

    public void save(User user, FcmTokenRequest fcmTokenRequest) {
        fcmTokenCacheService.save(user.getNickname(), fcmTokenRequest.getFcmToken());
    }

    public void sendNotification(String title, String message, String fcmToken)
        throws FirebaseMessagingException {
        firebaseMessaging.send(
            Message.builder()
                .setNotification(
                    Notification.builder()
                        .setTitle(title)
                        .setBody(message)
                        .build()
                ).setToken(fcmToken)
                .build()
        );
    }
}
