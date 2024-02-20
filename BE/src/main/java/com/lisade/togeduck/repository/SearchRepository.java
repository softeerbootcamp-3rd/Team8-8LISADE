package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Festival;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends JpaRepository<Festival, Long> {

    @Query("select f from Festival as f left join fetch FestivalImage")
    Slice<Festival> findByTitleContaining(Pageable pageable, String keyword);
}
