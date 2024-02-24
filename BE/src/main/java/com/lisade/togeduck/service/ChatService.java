package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.request.ChatMessageRequest;
import com.lisade.togeduck.entity.ChatMessage;
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
    public void save(ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = ChatMapper.toChatMessage(chatMessageRequest);
        chatRepository.save(chatMessage);
    }
}
