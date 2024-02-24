package com.lisade.togeduck.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {

    private Long roomId;
    @Setter
    private String message;
    private String action;
    private String createdAt;
    private String uuid;
    private String sender;
}
