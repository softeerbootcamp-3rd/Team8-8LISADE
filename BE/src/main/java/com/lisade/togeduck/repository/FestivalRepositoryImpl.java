package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QFestival.festival;

import com.lisade.togeduck.dto.response.BestFestivalDto;
import com.lisade.togeduck.dto.response.FestivalResponse;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
@Slf4j
public class FestivalRepositoryImpl implements FestivalRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BestFestivalDto> findBest(Integer limit) {
        return queryFactory.select(Projections.constructor(BestFestivalDto.class,
                festival.id,
                festival.thumbnailPath,
                festival.city.name,
                festival.title,
                festival.startedAt,
                festival.location
            ))
            .from(festival)
            .join(festival.city)
            .limit(limit)
            .orderBy(festival.popularScore.desc()).fetch();
    }

    @Override
    public List<FestivalResponse> findFestivalsByCategorySortedByScore(Long categoryId,
        Integer limit) {
        return queryFactory.select(Projections.constructor(FestivalResponse.class,
                festival.id,
                festival.title,
                festival.location,
                festival.startedAt,
                festival.endAt,
                festival.thumbnailPath
            )).from(festival)
            .where(festival.category.id.eq(categoryId)
                .and(festival.festivalStatus.eq(FestivalStatus.RECRUITMENT)))
            .orderBy(festival.popularScore.desc())
            .limit(limit)
            .fetch();
    }
}
