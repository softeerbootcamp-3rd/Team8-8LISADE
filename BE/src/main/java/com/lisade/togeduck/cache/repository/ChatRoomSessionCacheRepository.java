package com.lisade.togeduck.cache.repository;

import com.lisade.togeduck.cache.value.ChatRoomSessionCacheValue;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomSessionCacheRepository extends
    CrudRepository<ChatRoomSessionCacheValue, String> {

    Optional<ChatRoomSessionCacheValue> findByChatSession(String chatSession);
}
