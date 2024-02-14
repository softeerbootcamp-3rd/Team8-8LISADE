package com.lisade.togeduck.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BestFestivalResponse {

    Integer totalSize;
    List<Banner> banner;

    @Data
    @Builder
    @AllArgsConstructor
    public static class Banner {

        Long id;
        String path;
    }
}
