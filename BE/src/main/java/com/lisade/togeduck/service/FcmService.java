package com.lisade.togeduck.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.lisade.togeduck.dto.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendNotification(ChatMessageRequest chatMessageRequest)
        throws FirebaseMessagingException {
        firebaseMessaging.send(
            Message.builder()
                .putData("roomId", String.valueOf(chatMessageRequest.getRoomId()))
                .putData("roomName", chatMessageRequest.getRoomName())
                .putData("sender", chatMessageRequest.getSender())
                .putData("message", chatMessageRequest.getMessage())
                .putData("type", chatMessageRequest.getAction())
                .putData("createdAt", chatMessageRequest.getCreatedAt())
                .setTopic(String.valueOf(chatMessageRequest.getRoomId()))
                .build()
        );
    }
}
