package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.DistancePricesDto.BusInfo;
import java.util.List;

public interface BusCustomRepository {

    List<BusInfo> findBusInfoByDistance(Integer distance);
}