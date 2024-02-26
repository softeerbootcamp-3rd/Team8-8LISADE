package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.FestivalDetailResponse;
import com.lisade.togeduck.dto.response.FestivalResponse;
import com.lisade.togeduck.dto.response.FestivalRoutesResponse;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FestivalService {

    Slice<FestivalResponse> getList(Pageable pageable, Long categoryId,
        FestivalStatus festivalStatus,
        String filterType);

    Festival get(Long id);

    FestivalDetailResponse getDetail(User user, Long id);

    BestFestivalResponse getBest();

    Slice<FestivalRoutesResponse> getRoutes(Pageable pageable, Long id, String cityName);

    String getImage(Long id);
}
