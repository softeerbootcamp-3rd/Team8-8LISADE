package com.lisade.togeduck.cache.service;

import com.lisade.togeduck.cache.repository.FcmTokenCacheRepository;
import com.lisade.togeduck.cache.value.FcmTokenCacheValue;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmTokenCacheService {

    private final FcmTokenCacheRepository fcmTokenCacheRepository;

    public Optional<FcmTokenCacheValue> get(String nickname) {
        return fcmTokenCacheRepository.findByNickname(nickname);
    }

    public List<FcmTokenCacheValue> getList(List<String> nicknames) {
        return fcmTokenCacheRepository.findAllByNicknameIn(nicknames);
    }

    public void save(String nickname, String fcmToken) {
        Optional<FcmTokenCacheValue> fcmTokenCacheValue = get(nickname);

        if (fcmTokenCacheValue.isEmpty()) {
            fcmTokenCacheRepository.save(FcmTokenCacheValue.of(nickname, fcmToken));
        } else {
            fcmTokenCacheValue.get().setFcmToken(fcmToken);
            fcmTokenCacheRepository.save(fcmTokenCacheValue.get());
        }
    }
}
