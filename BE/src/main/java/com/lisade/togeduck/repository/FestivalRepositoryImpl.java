package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QFestival.festival;
import static com.lisade.togeduck.entity.QFestivalImage.festivalImage;
import static com.lisade.togeduck.entity.QView.view;

import com.lisade.togeduck.dto.response.BestFestivalDao;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FestivalRepositoryImpl implements FestivalRepositoryCustom {

    LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BestFestivalDao> findBest(Integer limit) {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        return queryFactory.select(Projections.constructor(BestFestivalDao.class,
                festival.id,
                festivalImage.path.as("path")
            ))
            .from(view)
            .leftJoin(festival).on(festival.id.eq(view.festival.id)
                .and(view.measurementAt.after(oneWeekAgo)))
            .leftJoin(festival.festivalImages, festivalImage)
            .on(festival.id.eq(festivalImage.festival.id)
                .and(festivalImage.id.eq(
                        JPAExpressions.select(festivalImage.id.min())
                            .from(festivalImage)
                            .where(festivalImage.festival.id.eq(festival.id))
                    )
                ))
            .groupBy(festival.id)
            .limit(limit)
            .orderBy(view.count.sum().desc())
            .fetch();
    }
}
