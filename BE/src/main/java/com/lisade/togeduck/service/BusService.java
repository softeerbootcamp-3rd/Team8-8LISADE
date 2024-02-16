package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.BusLayoutDto;
import com.lisade.togeduck.dto.response.DistancePricesDto.BusInfo;
import com.lisade.togeduck.entity.Bus;
import com.lisade.togeduck.exception.BusNotFoundException;
import com.lisade.togeduck.repository.BusRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;

    public Bus get(Long busId) {
        return busRepository.findBusByIdWithPriceTable(busId)
            .orElseThrow(BusNotFoundException::new);
    }

    public Bus get(Long busId, Integer distance) {
        return busRepository.findBusByIdAndDistanceWithPriceTable(busId, distance)
            .orElseThrow(BusNotFoundException::new);
    }

    public List<BusInfo> getBusInfo(Integer distance) {
        return busRepository.findBusInfoByDistance(distance);
    }

    public BusLayoutDto getBusLayout(Long routeId) {
        return busRepository.findBusLayoutByRouteId(routeId);
    }
}
