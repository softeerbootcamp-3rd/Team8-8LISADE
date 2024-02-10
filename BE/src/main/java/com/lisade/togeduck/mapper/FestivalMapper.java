package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.FestivalDetailDto;
import com.lisade.togeduck.dto.FestivalDto;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.FestivalImage;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public interface FestivalMapper {

    FestivalMapper INSTANCE = Mappers.getMapper(FestivalMapper.class);

    @Mapping(target = "paths", source = "festivalImages")
    FestivalDto toFestivalDto(Festival festival);

    @Mapping(target = "paths", source = "festivalImages")
    @Mapping(target = "category", source = "category.type")
    FestivalDetailDto toFestivalDetailDto(Festival festival);

    default List<String> festivalImagesToPaths(List<FestivalImage> festivalImages) {
        return festivalImages.stream()
            .map(FestivalImage::getPath)
            .toList();
    }

    default Slice<FestivalDto> toFestivalDtoSlice(Slice<Festival> festivals) {
        return festivals.map(this::toFestivalDto);
    }
}
