package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.chat.ChatRoom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class ChatRoomRepository {

    private static final Map<String, ChatRoom> chatRoomMap = new ConcurrentHashMap<>(); // db로 변경할 예정

    public ChatRoom findRoomById(String id) {
        return chatRoomMap.get(id);
    }

    public String createChatRoom(String name) {
        ChatRoom room = ChatRoom.create(name);
        chatRoomMap.put(room.getRoomId(), room);

        return room.getRoomId();
    }
}
