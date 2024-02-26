package com.lisade.togeduck.cache.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash("chat_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomSessionCacheValue {

    @Id
    private String id;
    @Indexed
    private String chatSession;
    @Indexed
    private String loginSession;

    private ChatRoomSessionCacheValue(String chatSession, String loginSession) {
        this.chatSession = chatSession;
        this.loginSession = loginSession;
    }


    public static ChatRoomSessionCacheValue of(String chatSession, String loginSession) {
        return new ChatRoomSessionCacheValue(chatSession, loginSession);
    }
}
