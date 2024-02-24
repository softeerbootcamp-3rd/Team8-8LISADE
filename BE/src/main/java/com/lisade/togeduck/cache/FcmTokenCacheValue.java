package com.lisade.togeduck.cache;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash("fcm_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmTokenCacheValue {

    @Id
    private String id;
    @Indexed
    private String nickname;
    private String fcmToken;

    private FcmTokenCacheValue(String nickname, String fcmToken) {
        this.nickname = nickname;
        this.fcmToken = fcmToken;
    }

    public static FcmTokenCacheValue of(String nickname, String fcmToken) {
        return new FcmTokenCacheValue(nickname, fcmToken);
    }
}
