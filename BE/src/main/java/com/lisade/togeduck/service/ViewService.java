package com.lisade.togeduck.service;

import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.repository.ViewRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewService {

    private final ViewRepository viewRepository;

    @Transactional
    public void add(Festival festival) {
        LocalDate measurementAt = LocalDate.now();
        viewRepository.add(festival, measurementAt);
    }
}
