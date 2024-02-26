package com.lisade.togeduck.controller;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.dto.response.ChatJoinResponse;
import com.lisade.togeduck.dto.response.ChatRoomResponse;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.service.ChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/rooms")
    public List<ChatRoomResponse> getList(@Login User user) {
        return chatRoomService.getList(user);
    }

    @PostMapping
    public ChatJoinResponse join(@Login User user, @RequestParam("routeId") Long routeId) {
        return chatRoomService.join(user, routeId);
    }
}
