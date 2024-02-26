package com.lisade.togeduck.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FestivalViewDto {

    Long id;
    String title;
    String location;
    Long categoryId;
    LocalDateTime startedAt;
    LocalDateTime endAt;
    LocalDateTime createdAt;
    String thumbnailPath;
    Integer weeklyViews;
}
