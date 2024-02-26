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

    public Optional<ChatRoomSessionCacheValue> get(String chatSession) {
        return chatRoomSessionCacheRepository.findByChatSession(chatSession);
    }

    public void save(ChatRoomSessionCacheValue chatRoomSessionCacheValue) {
        chatRoomSessionCacheRepository.save(chatRoomSessionCacheValue);
    }

    public void delete(String chatSession) {
        Optional<ChatRoomSessionCacheValue> chatRoomSessionCacheValue = get(chatSession);

        chatRoomSessionCacheValue.ifPresent(chatRoomSessionCacheRepository::delete);
    }
}
