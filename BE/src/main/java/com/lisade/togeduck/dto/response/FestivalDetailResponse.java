package com.lisade.togeduck.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FestivalDetailResponse {

    private Long id;
    private String title;
    private String category;
    private String location;
    private String content;
    private List<String> paths;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime startedAt;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime endAt;
}
