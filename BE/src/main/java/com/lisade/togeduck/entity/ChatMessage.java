package com.lisade.togeduck.entity;

import com.lisade.togeduck.entity.enums.MessageAction;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "chatMessage")
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String userName;
    private String message;
    private int roomNumber;
    private LocalDateTime messageTime;
    private MessageAction messageAction;

}
