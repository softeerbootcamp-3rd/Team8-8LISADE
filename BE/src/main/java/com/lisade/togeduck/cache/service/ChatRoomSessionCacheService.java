package com.lisade.togeduck.cache.service;

import com.lisade.togeduck.cache.repository.ChatRoomSessionCacheRepository;
import com.lisade.togeduck.cache.value.ChatRoomSessionCacheValue;
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

    public void addSession(String roomId, String nickname) {
        Optional<ChatRoomSessionCacheValue> chatRoomSessionCacheValue = get(roomId);

        if (chatRoomSessionCacheValue.isPresent()) {
            chatRoomSessionCacheValue.get().addNickname(nickname);
            save(chatRoomSessionCacheValue.get());
        } else {
            ChatRoomSessionCacheValue newChatRoomSessionCacheValue = ChatRoomSessionCacheValue.of(
                roomId);
            newChatRoomSessionCacheValue.addNickname(nickname);

            save(newChatRoomSessionCacheValue);
        }
    }

    public void deleteSession(String roomId, String nickname) {
        Optional<ChatRoomSessionCacheValue> chatRoomSessionCacheValue = get(roomId);

        if (chatRoomSessionCacheValue.isPresent()) {
            chatRoomSessionCacheValue.get().deleteNickname(nickname);
            save(chatRoomSessionCacheValue.get());
        }
    }
}
