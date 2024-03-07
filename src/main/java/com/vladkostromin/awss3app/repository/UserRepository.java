package com.vladkostromin.awss3app.repository;

import com.vladkostromin.awss3app.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<UserEntity, Long> {
    Mono<UserEntity> findByUsername(String username);

    @Query("UPDATE aws.users SET status = 'DELETED' where id = :id")
    Mono<Void> safeDeleteUser(Long id);

}
