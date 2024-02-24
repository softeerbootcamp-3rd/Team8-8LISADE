package com.lisade.togeduck.cache;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomSessionCacheRepository extends
    CrudRepository<ChatRoomSessionCacheValue, String> {

    Optional<ChatRoomSessionCacheValue> findByRoomId(String roomId);
}
