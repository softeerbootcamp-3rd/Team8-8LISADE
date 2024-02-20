package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.FestivalResponse;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.mapper.FestivalMapper;
import com.lisade.togeduck.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final FestivalMapper festivalMapper;

    public Slice<FestivalResponse> getList(Pageable pageable, String keyword) {
        Slice<Festival> festivals = searchRepository.findByTitleContaining(pageable,
            keyword);
        return festivalMapper.toFestivalResponseSlice(festivals);
    }
}
