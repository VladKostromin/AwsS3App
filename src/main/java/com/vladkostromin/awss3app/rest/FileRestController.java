package com.vladkostromin.awss3app.rest;

import com.vladkostromin.awss3app.dto.FileDto;
import com.vladkostromin.awss3app.dto.FileRequestDto;
import com.vladkostromin.awss3app.dto.FileResponseDto;
import com.vladkostromin.awss3app.entity.FileEntity;
import com.vladkostromin.awss3app.mapper.FileMapper;
import com.vladkostromin.awss3app.security.CustomPrincipal;
import com.vladkostromin.awss3app.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/file")
@RequiredArgsConstructor
public class FileRestController {

    private final FileService fileService;
    private final FileMapper fileMapper;


    @PostMapping("/upload")
    public Mono<FileDto> uploadFile(@RequestBody FileDto dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        Long userId = customPrincipal.getId();
        FileEntity fileEntity = fileMapper.map(dto);
        return fileService.uploadFile(fileEntity, userId).map(fileMapper::map);
    }

    @GetMapping("/download")
    public Mono<FileDto> download(@RequestBody FileDto dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        Long userId = customPrincipal.getId();
        FileEntity fileEntity = fileMapper.map(dto);
        return fileService.downloadFile(fileEntity, userId).map(fileMapper::map);
    }
}
