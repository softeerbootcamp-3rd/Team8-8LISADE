package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Seat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long>, SeatRepositoryCustom {

    Optional<Seat> findByRouteIdAndNo(Long routeId, Integer no);
}
