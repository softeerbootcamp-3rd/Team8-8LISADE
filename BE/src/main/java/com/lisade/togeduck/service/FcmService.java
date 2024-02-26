package com.lisade.togeduck.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.lisade.togeduck.constant.FcmType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendNotification(String title, String sender, String message, FcmType fcmType,
        String id)
        throws FirebaseMessagingException {
        firebaseMessaging.send(
            Message.builder()
                .putData("title", title)
                .putData("sender", sender)
                .putData("message", message)
                .putData("type", fcmType.getType())
                .setTopic(id)
                .build()
        );
    }
}
