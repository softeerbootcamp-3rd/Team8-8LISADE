package com.lisade.togeduck.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FestivalResponse {

    private Long id;
    private String title;
    private String location;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime startedAt;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime endAt;
    private String thumbnailPath;

    public FestivalResponse(Long id, String title, String location, LocalDateTime startedAt,
        LocalDateTime endAt,
        String thumbnailPath) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.startedAt = startedAt;
        this.endAt = endAt;
        this.thumbnailPath = thumbnailPath;
    }
}
