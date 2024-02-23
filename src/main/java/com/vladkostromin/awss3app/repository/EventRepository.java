package com.vladkostromin.awss3app.repository;

import com.vladkostromin.awss3app.entity.EventEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface EventRepository extends R2dbcRepository<EventEntity, Long> {

    @Query("SELECT * FROM aws.events WHERE  user_id =:userId")
    Flux<EventEntity> findByUserId(Long userId);
}
