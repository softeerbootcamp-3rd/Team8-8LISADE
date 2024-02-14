package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.BestFestivalDao;
import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.BestFestivalResponse.Banner;
import com.lisade.togeduck.dto.response.FestivalDetailDto;
import com.lisade.togeduck.dto.response.FestivalDto;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.FestivalImage;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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

    @Mapping(target = "totalSize", source = "totalSize")
    @Mapping(target = "banner", source = "bestFestivalDao", qualifiedByName = "toBanner")
    BestFestivalResponse toBestFestivalResponse(Integer totalSize,
        List<BestFestivalDao> bestFestivalDao);

    @Named("toBanner")
    default List<Banner> daoToBanner(List<BestFestivalDao> bestFestivalDao) {
        return bestFestivalDao.stream()
            .map(dao -> Banner.builder().id(dao.getId()).path(dao.getPath()).build())
            .toList();
    }

    default List<String> festivalImagesToPaths(List<FestivalImage> festivalImages) {
        return festivalImages.stream()
            .map(FestivalImage::getPath)
            .toList();
    }

    default Slice<FestivalDto> toFestivalDtoSlice(Slice<Festival> festivals) {
        return festivals.map(this::toFestivalDto);
    }
}
