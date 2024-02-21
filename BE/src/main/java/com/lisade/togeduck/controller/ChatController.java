package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;

    // /pub/chat/enter
    @MessageMapping(value = "/chat/enter")
    public void enter(@RequestBody ChatMessage message) {
        message.setMessage(message.getWriter() + "님이 채팅방에 참여했습니다.");
        template.convertAndSend("/queue/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    public void message(@RequestBody ChatMessage message) {
        template.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);

    }
}
