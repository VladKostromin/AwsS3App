package com.vladkostromin.awss3app.service;

import com.vladkostromin.awss3app.entity.EventEntity;
import com.vladkostromin.awss3app.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;

    public Mono<EventEntity> getEntityById(Long id) {
        return eventRepository.findById(id);
    }

    public Mono<EventEntity> saveEntity(EventEntity event) {
        return eventRepository.save(event);
    }


}
