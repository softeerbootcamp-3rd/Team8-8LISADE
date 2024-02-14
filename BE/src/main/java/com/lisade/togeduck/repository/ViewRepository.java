package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QView.view;

import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.View;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ViewRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public void add(Festival festival, LocalDate measurementAt) {
        long affectedRows = updateCount(festival, measurementAt);

        if (affectedRows == 0) {
            create(festival, measurementAt);
        }
    }

    private void create(Festival festival, LocalDate measurementAt) {
        View viewEntity = View.builder().count(1).measurementAt(measurementAt).festival(festival)
            .build();
        entityManager.persist(viewEntity);
    }

    private long updateCount(Festival festival, LocalDate measurementAt) {
        return queryFactory.update(view)
            .set(view.count, view.count.add(1))
            .where(view.festival.id.eq(festival.getId())
                .and(view.measurementAt.eq(measurementAt)))
            .execute();
    }
}
