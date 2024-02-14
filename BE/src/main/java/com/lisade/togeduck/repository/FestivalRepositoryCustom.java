package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.BestFestivalDao;
import java.util.List;

public interface FestivalRepositoryCustom {

    List<BestFestivalDao> findBest(Integer limit);
}
