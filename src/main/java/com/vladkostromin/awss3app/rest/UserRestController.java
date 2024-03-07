package com.vladkostromin.awss3app.rest;


import com.vladkostromin.awss3app.dto.UserDto;
import com.vladkostromin.awss3app.entity.UserEntity;
import com.vladkostromin.awss3app.mapper.FileMapper;
import com.vladkostromin.awss3app.mapper.UserMapper;
import com.vladkostromin.awss3app.security.CustomPrincipal;
import com.vladkostromin.awss3app.service.FileService;
import com.vladkostromin.awss3app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final UserMapper userMapper;
    @PostMapping("/update")
    public Mono<UserDto> updateUser(@RequestBody UserDto dto) {
        UserEntity user = userMapper.map(dto);
        return userService.updateUser(user).map(userMapper::map);
    }

    @GetMapping("/info")
    public Mono<UserDto> getInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return userService.getUserById(customPrincipal.getId()).map(userMapper::map);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<String>> deleteUser(@RequestBody UserDto dto) {
        return userService.deleteUserById(dto.getId())
                .then(Mono.just(ResponseEntity.ok("User successfully deleted")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/safe_delete")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public Mono<ResponseEntity<String>> safeDeleteUser(@RequestBody UserDto dto) {
        return userService.safeDeleteUserById(dto.getId())
                .then(Mono.just(ResponseEntity.ok("User successfully safe deleted")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
