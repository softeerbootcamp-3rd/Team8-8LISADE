package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.request.ChatJoinRequest;
import com.lisade.togeduck.dto.request.ChatMessageRequest;
import com.lisade.togeduck.mapper.ChatMapper;
import com.lisade.togeduck.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    @MessageMapping(value = "/chat/join")
    public void join(@RequestBody ChatJoinRequest chatJoinRequest) {
        String message = chatJoinRequest.getSender() + "님이 입장하셨습니다.";

        ChatMessageRequest chatMessageRequest = ChatMapper.toChatMessageRequest(chatJoinRequest,
            message);
        chatService.save(chatMessageRequest);

        simpleMessageSendingOperations.convertAndSend(
            "/topic/message/" + chatJoinRequest.getRoomId(), chatMessageRequest);
    }

    @MessageMapping(value = "/chat/message")
    public void message(@RequestBody ChatMessageRequest chatMessageRequest) {
        chatService.save(chatMessageRequest);

        simpleMessageSendingOperations.convertAndSend(
            "/topic/message/" + chatMessageRequest.getRoomId(), chatMessageRequest);
    }
}
