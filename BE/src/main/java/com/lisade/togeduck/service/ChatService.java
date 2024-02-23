package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.ChatMessageResponse;
import com.lisade.togeduck.entity.ChatMessage;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.mapper.ChatMapper;
import com.lisade.togeduck.repository.ChatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    @Transactional
    public void save(User user, ChatMessageResponse chatMessageResponse) {
        ChatMessage chatMessage = ChatMapper.toChatMessage(user, chatMessageResponse);
        chatRepository.save(chatMessage);
    }
}
