package com.lisade.togeduck.controller;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.FestivalDetailResponse;
import com.lisade.togeduck.dto.response.FestivalResponse;
import com.lisade.togeduck.dto.response.FestivalRoutesResponse;
import com.lisade.togeduck.entity.User;
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
    public Slice<FestivalResponse> getList(
        @PageableDefault(size = 10, sort = "startedAt", direction = Direction.ASC) Pageable pageable,
        @RequestParam(name = "category", required = true) Long categoryId,
        @RequestParam(name = "filter", required = false) String filterType,
        @RequestParam(name = "festivalStatus", required = false, defaultValue = "1") FestivalStatus festivalStatus) {
        return festivalService.getList(pageable, categoryId, festivalStatus, filterType);
    }

    @GetMapping("/{id}")
    public FestivalDetailResponse getDetail(@Login User user, @PathVariable(value = "id") Long id) {
        return festivalService.getDetail(user, id);
    }

    @GetMapping("/best")
    public BestFestivalResponse getBest() {
        return festivalService.getBest();
    }

    @GetMapping("/{id}/routes")
    public Slice<FestivalRoutesResponse> getRoutes(
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
        @PathVariable(value = "id") Long id,
        @RequestParam(name = "city", required = false, defaultValue = "all") String cityName) {
        return festivalService.getRoutes(pageable, id, cityName);
    }

    @GetMapping("/{festival_id}/images")
    public String getFestivalImage(@PathVariable(value = "festival_id") Long id) {
        return festivalService.getImage(id);
    }
}
