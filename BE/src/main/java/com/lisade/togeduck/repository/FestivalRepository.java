package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.enums.Category;
import com.lisade.togeduck.entity.enums.FestivalStatus;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long>,
    FestivalRepositoryCustom {

    @Query("SELECT DISTINCT f FROM Festival f LEFT JOIN FETCH f.festivalImages")
    Slice<Festival> findSliceByCategoryAndFestivalStatus(final Pageable pageable, Category category,
        FestivalStatus festivalStatus);

    @Override
    @Query("SELECT DISTINCT f FROM Festival f LEFT JOIN FETCH f.festivalImages WHERE f.id = :id")
    Optional<Festival> findById(Long id);
    
}
