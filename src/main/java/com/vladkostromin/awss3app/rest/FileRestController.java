package com.vladkostromin.awss3app.rest;

import com.vladkostromin.awss3app.dto.FileDto;
import com.vladkostromin.awss3app.entity.FileEntity;
import com.vladkostromin.awss3app.mapper.FileMapper;
import com.vladkostromin.awss3app.security.CustomPrincipal;
import com.vladkostromin.awss3app.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER','ADMIN','MODERATOR')")
    public Mono<FileDto> uploadFile(@RequestBody FileDto dto, Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        Long userId = customPrincipal.getId();
        FileEntity fileEntity = fileMapper.map(dto);
        return fileService.uploadFile(fileEntity, userId).map(fileMapper::map);
    }

    @GetMapping("/download")
    @PreAuthorize("hasAnyRole('USER','ADMIN','MODERATOR')")
    public Mono<FileDto> downloadFile(@RequestBody FileDto dto) {
        FileEntity fileEntity = fileMapper.map(dto);
        return fileService.downloadFile(fileEntity).map(fileMapper::map);
    }

    @DeleteMapping("/safe_delete")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public Mono<ResponseEntity<String>> safeDeleteFile(@RequestBody FileDto dto) {
        FileEntity fileEntity = fileMapper.map(dto);
        return fileService.deleteFile(fileEntity)
                .then(Mono.just(ResponseEntity.ok("File successfully removed")))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
