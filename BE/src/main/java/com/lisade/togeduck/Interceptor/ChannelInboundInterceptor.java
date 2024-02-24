package com.lisade.togeduck.Interceptor;

import static com.lisade.togeduck.constant.SessionConst.LOGIN_USER;

import com.lisade.togeduck.cache.ChatRoomSessionCacheService;
import com.lisade.togeduck.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelInboundInterceptor implements ChannelInterceptor {

    private final ChatRoomSessionCacheService chatRoomSessionCacheService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
            RequestContextHolder.getRequestAttributes())).getRequest();
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(LOGIN_USER.getSessionName());

        log.info(message.getHeaders().get("simpDestination").toString());
        log.info(user.getNickname());

        String dest = String.valueOf(message.getHeaders().get("simpDestination"));

        if (Objects.requireNonNull(accessor.getCommand()) == StompCommand.SUBSCRIBE) {
            String fcmToken = String.valueOf(accessor.getFirstNativeHeader("Authorization"));
            chatRoomSessionCacheService.deleteSession(dest, fcmToken);

        } else if (Objects.requireNonNull(accessor.getCommand()) == StompCommand.DISCONNECT) {
            String fcmToken = String.valueOf(accessor.getFirstNativeHeader("Authorization"));

            chatRoomSessionCacheService.addSession(dest, fcmToken);
        }
    }
}
