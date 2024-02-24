package com.lisade.togeduck.cache.repository;

import com.lisade.togeduck.cache.value.SessionCacheValue;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionCacheRepository extends CrudRepository<SessionCacheValue, String> {

    Optional<SessionCacheValue> findByNickname(String nickname);

    Optional<SessionCacheValue> findBySession(String session);
}
