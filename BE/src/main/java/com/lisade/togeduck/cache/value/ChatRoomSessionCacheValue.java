package com.lisade.togeduck.cache.value;

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
@RedisHash("chat_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomSessionCacheValue {

    @Id
    private String id;
    @Indexed
    private String roomId;
    private List<String> nicknames;

    private ChatRoomSessionCacheValue(String roomId) {
        this.roomId = roomId;
        this.nicknames = new ArrayList<>();
    }

    public void addNickname(String nickname) {
        nicknames.add(nickname);
    }

    public void deleteNickname(String nickname) {
        nicknames.remove(nickname);
    }

    public static ChatRoomSessionCacheValue of(String roomId) {
        return new ChatRoomSessionCacheValue(roomId);
    }
}
