package com.lisade.togeduck.controller;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.dto.request.ChatJoinRequest;
import com.lisade.togeduck.dto.request.ChatMessageRequest;
import com.lisade.togeduck.dto.response.ChatMessageResponse;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.mapper.ChatMapper;
import com.lisade.togeduck.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    @MessageMapping(value = "/chat/join")
    public void join(@Login User user, @RequestBody ChatJoinRequest chatJoinRequest) {
        String message = user.getNickname() + "님이 입장하셨습니다.";

        ChatMessageResponse chatMessageResponse = ChatMapper.toChatMessageResponse(user,
            chatJoinRequest, message);
        chatService.save(user, chatMessageResponse);

        simpleMessageSendingOperations.convertAndSend(
            "/topic/message/" + chatJoinRequest.getRoomId(), chatMessageResponse);
    }

    @MessageMapping(value = "/chat/message")
    public void message(@Login User user, @RequestBody ChatMessageRequest chatMessageRequest) {
        ChatMessageResponse chatMessageResponse = ChatMapper.toChatMessageResponse(user,
            chatMessageRequest);
        chatService.save(user, chatMessageResponse);

        simpleMessageSendingOperations.convertAndSend(
            "/topic/message/" + chatMessageRequest.getRoomId(), chatMessageResponse);
    }
}
