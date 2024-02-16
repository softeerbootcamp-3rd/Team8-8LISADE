package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Bus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long>, BusCustomRepository {

    @Query("select b from Bus b join fetch b.priceTables where b.id =:busId")
    Optional<Bus> findBusByIdWithPriceTable(Long busId);

    @Query("select b from Bus b join fetch b.priceTables as pt where b.id =:busId and pt.distance < :distance order by pt.distance desc limit 1")
    Optional<Bus> findBusByIdAndDistanceWithPriceTable(Long busId, Integer distance);
}
