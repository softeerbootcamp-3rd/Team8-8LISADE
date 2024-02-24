package com.lisade.togeduck.cache;

import java.util.ArrayList;
import java.util.List;
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
public class ChatRoomSessionCacheValue {

    @Id
    private String id;
    @Indexed
    private String roomId;
    private List<String> fcmTokens;

    private ChatRoomSessionCacheValue(String roomId) {
        this.roomId = roomId;
        this.fcmTokens = new ArrayList<>();
    }

    public void addFcmToken(String fcmToken) {
        fcmTokens.add(fcmToken);
    }

    public void deleteFcmToken(String fcmToken) {
        fcmTokens.remove(fcmToken);
    }

    public static ChatRoomSessionCacheValue of(String roomId) {
        return new ChatRoomSessionCacheValue(roomId);
    }
}
