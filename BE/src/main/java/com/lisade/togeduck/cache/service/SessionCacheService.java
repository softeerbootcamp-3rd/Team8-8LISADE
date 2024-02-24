package com.lisade.togeduck.cache.service;

import com.lisade.togeduck.cache.repository.SessionCacheRepository;
import com.lisade.togeduck.cache.value.SessionCacheValue;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionCacheService {

    private final SessionCacheRepository sessionCacheRepository;

    public Optional<SessionCacheValue> getByNickname(String nickname) {
        return sessionCacheRepository.findByNickname(nickname);
    }

    public Optional<SessionCacheValue> getBySession(String session) {
        return sessionCacheRepository.findBySession(session);
    }

    public void save(String nickname, String session) {
        Optional<SessionCacheValue> sessionCacheValue = getByNickname(nickname);

        if (sessionCacheValue.isPresent()) {
            sessionCacheValue.get().setSession(session);

            sessionCacheRepository.save(sessionCacheValue.get());
        } else {
            SessionCacheValue newSessionCacheValue = SessionCacheValue.of(nickname, session);
            sessionCacheRepository.save(newSessionCacheValue);
        }
    }
}
