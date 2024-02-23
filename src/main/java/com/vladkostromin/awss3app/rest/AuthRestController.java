package com.vladkostromin.awss3app.rest;

import com.vladkostromin.awss3app.dto.AuthRequestDto;
import com.vladkostromin.awss3app.dto.AuthResponseDto;
import com.vladkostromin.awss3app.dto.FileDto;
import com.vladkostromin.awss3app.dto.UserDto;
import com.vladkostromin.awss3app.entity.FileEntity;
import com.vladkostromin.awss3app.entity.UserEntity;
import com.vladkostromin.awss3app.mapper.FileMapper;
import com.vladkostromin.awss3app.mapper.UserMapper;
import com.vladkostromin.awss3app.security.CustomPrincipal;
import com.vladkostromin.awss3app.security.SecurityService;
import com.vladkostromin.awss3app.service.FileService;
import com.vladkostromin.awss3app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.activation.DataSource;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestController {
    private final SecurityService securityService;
    private final UserService userService;

    private final UserMapper userMapper;


    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto dto) {
        UserEntity entity = userMapper.map(dto);
        return userService.registerUser(entity)
                .map(userMapper::map);
    }
    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        return securityService
                .authenticate(authRequestDto.getUsername(), authRequestDto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issueAt(tokenDetails.getIssuedAt())
                                .expiredAt(tokenDetails.getExpiresAt())
                                .build()
                ));

    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return userService.getUserById(customPrincipal.getId()).map(userMapper::map);
    }
}
