package com.lisade.togeduck.cache;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomSessionCacheService {

    private final ChatRoomSessionCacheRepository chatRoomSessionCacheRepository;

    public Optional<ChatRoomSessionCacheValue> get(String roomId) {
        return chatRoomSessionCacheRepository.findByRoomId(roomId);
    }

    public void save(ChatRoomSessionCacheValue chatRoomSessionCacheValue) {
        chatRoomSessionCacheRepository.save(chatRoomSessionCacheValue);
    }

    public void addSession(String roomId, String fcmToken) {
        Optional<ChatRoomSessionCacheValue> chatRoomSessionCacheValue = get(roomId);

        if (chatRoomSessionCacheValue.isPresent()) {
            chatRoomSessionCacheValue.get().addFcmToken(fcmToken);
            save(chatRoomSessionCacheValue.get());
        } else {
            ChatRoomSessionCacheValue newChatRoomSessionCacheValue = ChatRoomSessionCacheValue.of(
                roomId);
            newChatRoomSessionCacheValue.addFcmToken(fcmToken);

            save(newChatRoomSessionCacheValue);
        }
    }

    public void deleteSession(String roomId, String fcmToken) {
        Optional<ChatRoomSessionCacheValue> chatRoomSessionCacheValue = get(roomId);

        if (chatRoomSessionCacheValue.isPresent()) {
            chatRoomSessionCacheValue.get().deleteFcmToken(fcmToken);
            save(chatRoomSessionCacheValue.get());
        }
    }
}
