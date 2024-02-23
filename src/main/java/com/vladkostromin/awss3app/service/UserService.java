package com.vladkostromin.awss3app.service;

import com.vladkostromin.awss3app.entity.EventEntity;
import com.vladkostromin.awss3app.entity.UserEntity;
import com.vladkostromin.awss3app.enums.Role;
import com.vladkostromin.awss3app.enums.UserStatus;
import com.vladkostromin.awss3app.repository.EventRepository;
import com.vladkostromin.awss3app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EventRepository eventRepository;

    public Mono<UserEntity> registerUser(UserEntity user) {
        return userRepository.save(
                user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .role(Role.USER)
                        .status(UserStatus.ACTIVE)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        ).doOnSuccess(u -> log.info("IN registerUser - user: {} created", u)).cast(UserEntity.class);
    }

    public Mono<UserEntity> getUserById(Long id) {
        Mono<UserEntity> userMono = userRepository.findById(id);
        Flux<EventEntity> events = eventRepository.findByUserId(id);

        return userMono.flatMap(user -> events.collectList()
                .map(eventEntities -> {
                    user.setEvents(eventEntities);
                    return user;
        }));
    }

    public Mono<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Mono<Void> deleteUserById(Long id) {
        return userRepository.deleteById(id);
    }

//    public Flux<UserEntity> getAllActiveUsers() {
//        return userRepository.file();
//    }
//    public Flux<UserEntity> getAllInactiveUsers() {
//        return userRepository.findAllInactiveUsers();
//    }

}
