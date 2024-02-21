package com.lisade.togeduck.service;

import com.lisade.togeduck.entity.ChatRoom;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.repository.ChatRoomRepository;
import com.lisade.togeduck.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final RouteRepository routeRepository;

    public void create(Long routeId, Long festivalId) {
        Route findRoute = routeRepository.findById(routeId)
            .orElseThrow(RouteNotFoundException::new);
        ChatRoom chatRoom = ChatRoom.builder()
            .route(findRoute)
            .roomName(festivalId + " 채팅방")
            .build();
        chatRoomRepository.save(chatRoom);
    }
}
