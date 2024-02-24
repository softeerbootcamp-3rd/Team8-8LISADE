package com.lisade.togeduck.cache.value;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash("session")
public class SessionCacheValue {

    @Id
    private String id;
    @Indexed
    private String nickname;
    @Indexed
    private String session;

    private SessionCacheValue(String nickname, String session) {
        this.nickname = nickname;
        this.session = session;
    }

    public static SessionCacheValue of(String nickname, String session) {
        return new SessionCacheValue(nickname, session);
    }
}
