package com.lisade.togeduck.cache.service;

import com.lisade.togeduck.cache.repository.BestFestivalCacheRepository;
import com.lisade.togeduck.cache.value.BestFestivalCacheValue;
import com.lisade.togeduck.dto.response.BestFestivalResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BestFestivalCacheService {

    public static final String BEST_CACHE_ID = "best";
    private final BestFestivalCacheRepository bestFestivalCacheRepository;

    public void save(BestFestivalResponse bestFestivalResponse) {
        BestFestivalCacheValue best = new BestFestivalCacheValue(bestFestivalResponse);
        bestFestivalCacheRepository.save(best);
    }

    public Optional<BestFestivalCacheValue> get() {
        return bestFestivalCacheRepository.findById(BEST_CACHE_ID);
    }
}
