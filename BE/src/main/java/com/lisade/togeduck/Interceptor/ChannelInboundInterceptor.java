package com.lisade.togeduck.Interceptor;

import com.lisade.togeduck.cache.service.ChatRoomSessionCacheService;
import com.lisade.togeduck.cache.service.SessionCacheService;
import com.lisade.togeduck.cache.value.ChatRoomSessionCacheValue;
import com.lisade.togeduck.cache.value.SessionCacheValue;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelInboundInterceptor implements ChannelInterceptor {

    private final SessionCacheService sessionCacheService;
    private final ChatRoomSessionCacheService chatRoomSessionCacheService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getCommand() == StompCommand.SEND) {
            String payload = new String((byte[]) message.getPayload());
            String chatSession = accessor.getSessionId();
            Optional<ChatRoomSessionCacheValue> chatRoomSessionCacheValue = chatRoomSessionCacheService.get(
                chatSession);

            if (chatRoomSessionCacheValue.isEmpty()) {
                return message;
            }

            Optional<SessionCacheValue> sessionCacheValue = sessionCacheService.getBySession(
                chatRoomSessionCacheValue.get().getLoginSession());

            if (sessionCacheValue.isEmpty()) {
                return message;
            }

            StringBuilder newPayload = new StringBuilder(payload);
            String additionMessage =
                ",\"sender\":\"" + sessionCacheValue.get().getNickname() + "\"}";
            newPayload.deleteCharAt(newPayload.length() - 1);
            newPayload.append(additionMessage);

            return MessageBuilder.withPayload(newPayload.toString())
                .copyHeaders(message.getHeaders())
                .build();
        }

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (accessor.getCommand() == StompCommand.CONNECT) {
            String loginSession = accessor.getFirstNativeHeader("Cookie");
            String chatSession = accessor.getSessionId();

            chatRoomSessionCacheService.save(
                ChatRoomSessionCacheValue.of(chatSession, loginSession));
        } else if (accessor.getCommand() == StompCommand.DISCONNECT) {
            String chatSession = accessor.getSessionId();

            chatRoomSessionCacheService.delete(chatSession);
        }
    }
}
