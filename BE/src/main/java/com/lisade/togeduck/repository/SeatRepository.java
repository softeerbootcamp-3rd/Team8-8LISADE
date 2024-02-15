package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Seat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("select s from Seat as s join fetch s.route as r join fetch r.bus")
    List<Seat> findAllByRouteId(Long routeId);

    Optional<Seat> findByRouteIdAndNo(Long routeId, Integer no);
}
