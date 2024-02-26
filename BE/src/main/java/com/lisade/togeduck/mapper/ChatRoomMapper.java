package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.ChatJoinResponse;
import com.lisade.togeduck.dto.response.ChatRoomResponse;
import com.lisade.togeduck.entity.ChatRoom;
import java.util.List;

public class ChatRoomMapper {

    public static ChatRoomResponse toChatRoomResponse(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
            .id(chatRoom.getId())
            .routeId(chatRoom.getRoute().getId())
            .roomName(chatRoom.getRoomName())
            .thumbnailPath(chatRoom.getThumbnailPath())
            .build();
    }

    public static List<ChatRoomResponse> toChatRoomResponseList(List<ChatRoom> chatRooms) {
        return chatRooms
            .stream()
            .map(ChatRoomMapper::toChatRoomResponse)
            .toList();
    }

    public static ChatJoinResponse toChatJoinResponse(Long chatRoomId, Long routeId) {
        return ChatJoinResponse.builder()
            .chatRoomId(chatRoomId)
            .routeId(routeId)
            .build();
    }
}
