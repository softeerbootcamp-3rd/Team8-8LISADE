package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.request.ChatJoinRequest;
import com.lisade.togeduck.dto.request.ChatMessageRequest;
import com.lisade.togeduck.entity.ChatMessage;
import com.lisade.togeduck.entity.enums.MessageAction;

public class ChatMapper {

    public static ChatMessage toChatMessage(ChatMessageRequest chatMessageRequest) {
        return ChatMessage.builder()
            .roomId(chatMessageRequest.getRoomId())
            .sender(chatMessageRequest.getSender())
            .message(chatMessageRequest.getMessage())
            .action(MessageAction.getMessageAction(chatMessageRequest.getAction()))
            .createdAt(chatMessageRequest.getCreatedAt()).build();
    }

    public static ChatMessageRequest toChatMessageRequest(ChatJoinRequest chatJoinRequest,
        String message) {
        return ChatMessageRequest.builder()
            .roomId(chatJoinRequest.getRoomId())
            .sender(chatJoinRequest.getSender())
            .message(message)
            .action(MessageAction.JOIN.name())
            .createdAt(chatJoinRequest.getCreatedAt())
            .uuid(chatJoinRequest.getUuid())
            .build();
    }
}
