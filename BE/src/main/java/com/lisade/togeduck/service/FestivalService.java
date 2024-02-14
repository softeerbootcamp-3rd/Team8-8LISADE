package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.FestivalDetailDto;
import com.lisade.togeduck.dto.response.FestivalDto;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.enums.Category;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FestivalService {

    Slice<FestivalDto> getList(Pageable pageable, Category category, FestivalStatus festivalStatus,
        String filterType);

    Festival get(Long id);

    FestivalDetailDto getDetail(Long id);

    BestFestivalResponse getBest();
}
