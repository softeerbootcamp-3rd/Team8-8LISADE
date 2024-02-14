package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.FestivalDetailDto;
import com.lisade.togeduck.dto.response.FestivalDto;
import com.lisade.togeduck.entity.enums.Category;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import com.lisade.togeduck.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/festivals")
public class FestivalController {

    private final FestivalService festivalService;

    @GetMapping
    public Slice<FestivalDto> getList(
        @PageableDefault(size = 10, sort = "startedAt", direction = Direction.ASC) Pageable pageable,
        @RequestParam(name = "category", required = true) Category category,
        @RequestParam(name = "filter", required = false) String filterType,
        @RequestParam(name = "festivalStatus", required = false, defaultValue = "1") FestivalStatus festivalStatus) {
        return festivalService.getList(pageable, category, festivalStatus, filterType);
    }

    @GetMapping("/{id}")
    public FestivalDetailDto getDetail(@PathVariable(value = "id") Long id) {
        return festivalService.getDetail(id);
    }

    @GetMapping("/best")
    public BestFestivalResponse getBest() {
        return festivalService.getBest();
    }
}
