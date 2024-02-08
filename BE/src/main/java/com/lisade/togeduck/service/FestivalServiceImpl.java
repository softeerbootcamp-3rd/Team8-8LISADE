package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.FestivalDto;
import com.lisade.togeduck.entity.Category;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.Status;
import com.lisade.togeduck.mapper.FestivalMapper;
import com.lisade.togeduck.repository.FestivalRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FestivalServiceImpl implements FestivalService {

    private final FestivalRepository festivalRepository;
    private final FestivalMapper festivalMapper;

    @Override
    public Slice<FestivalDto> getList(Pageable pageable, Category category, Status status,
        String filterType) {
        Slice<Festival> festivals = festivalRepository.findSliceByCategoryAndStatus(pageable,
            category, status);
        return festivalMapper.toFestivalDtoSlice(festivals);
    }

    @Override
    public Festival get(Long id) {
        Optional<Festival> optionalFestival = festivalRepository.findById(id);
        return optionalFestival.orElse(null);
    }
}