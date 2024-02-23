package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.request.ChatJoinRequest;
import com.lisade.togeduck.dto.request.ChatMessageRequest;
import com.lisade.togeduck.dto.response.ChatMessageResponse;
import com.lisade.togeduck.entity.ChatMessage;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.enums.MessageAction;

public class ChatMapper {

    public static ChatMessage toChatMessage(User user, ChatMessageResponse chatMessageResponse) {
        return ChatMessage.builder()
            .roomId(chatMessageResponse.getRoomId())
            .sender(user.getNickname())
            .message(chatMessageResponse.getMessage())
            .action(MessageAction.getMessageAction(chatMessageResponse.getAction()))
            .createdAt(chatMessageResponse.getCreatedAt()).build();
    }

    public static ChatMessageResponse toChatMessageResponse(User user,
        ChatMessageRequest chatMessageResponse) {
        return ChatMessageResponse.builder()
            .roomId(chatMessageResponse.getRoomId())
            .sender(user.getNickname())
            .message(chatMessageResponse.getMessage())
            .createdAt(chatMessageResponse.getCreatedAt())
            .action(chatMessageResponse.getAction())
            .build();
    }

    public static ChatMessageResponse toChatMessageResponse(User user,
        ChatJoinRequest chatJoinRequest, String message) {
        return ChatMessageResponse.builder()
            .roomId(chatJoinRequest.getRoomId())
            .sender(user.getNickname())
            .message(message)
            .createdAt(chatJoinRequest.getCreatedAt())
            .action(MessageAction.JOIN.name())
            .build();
    }
}
