package com.lisade.togeduck.entity;

import com.lisade.togeduck.entity.enums.MessageAction;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    private String id;
    private Long userId;
    private Long roomId;
    private String sender;
    private String message;
    private MessageAction action;
    private String createdAt;
}
