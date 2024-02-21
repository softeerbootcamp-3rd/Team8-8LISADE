package com.lisade.togeduck.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomListResponse {

    private Long id;
    private String roomName;
    private Integer numberOfMembers;
    private String thumbnailPath;
}
