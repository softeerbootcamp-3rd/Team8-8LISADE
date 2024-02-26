package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.ChatJoinResponse;
import com.lisade.togeduck.dto.response.ChatRoomResponse;
import com.lisade.togeduck.entity.ChatRoom;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.UserChatRoom;
import com.lisade.togeduck.exception.ChatRoomNotFoundException;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.mapper.ChatRoomMapper;
import com.lisade.togeduck.repository.ChatRoomRepository;
import com.lisade.togeduck.repository.RouteRepository;
import com.lisade.togeduck.repository.UserChatRoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final RouteRepository routeRepository;

    @Transactional
    public void create(Long routeId) {
        Route findRoute = routeRepository.findById(routeId)
            .orElseThrow(RouteNotFoundException::new);

        Festival festival = findRoute.getFestival();

        ChatRoom chatRoom = ChatRoom.builder()
            .route(findRoute)
            .roomName(festival.getTitle())
            .numberOfMembers(0)
            .thumbnailPath(festival.getThumbnailPath())
            .build();
        
        chatRoomRepository.save(chatRoom);
    }

    @Transactional
    public List<ChatRoomResponse> getList(User user) {
        List<ChatRoom> chatRoom = chatRoomRepository.findAllByUserId(user.getId());

        return ChatRoomMapper.toChatRoomResponseList(chatRoom);
    }

    public ChatJoinResponse join(User user, Long routeId) {
        ChatRoom chatRoom = chatRoomRepository.findByRouteId(routeId).orElseThrow(
            ChatRoomNotFoundException::new);

        userChatRoomRepository
            .save(UserChatRoom.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build());

        return ChatRoomMapper.toChatJoinResponse(chatRoom.getId(), routeId);
    }
}
