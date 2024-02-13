package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.QView;
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
        QView view = QView.view1;

        long affectedRows = updateCount(festival, measurementAt, view);

        if (affectedRows == 0) {
            create(festival, measurementAt);
        }
    }

    private void create(Festival festival, LocalDate measurementAt) {
        View viewEntity = View.builder().view(1).measurementAt(measurementAt).festival(festival)
            .build();
        entityManager.persist(viewEntity);
    }

    private long updateCount(Festival festival, LocalDate measurementAt, QView view) {
        return queryFactory.update(view)
            .set(view.view, view.view.add(1))
            .where(view.festival.id.eq(festival.getId())
                .and(view.measurementAt.eq(measurementAt)))
            .execute();
    }
}
