package com.vladkostromin.awss3app.service;

import com.vladkostromin.awss3app.entity.EventEntity;
import com.vladkostromin.awss3app.entity.FileEntity;
import com.vladkostromin.awss3app.enums.EventStatus;
import com.vladkostromin.awss3app.enums.FileStatus;
import com.vladkostromin.awss3app.repository.EventRepository;
import com.vladkostromin.awss3app.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {
    private final S3AsyncClient s3AsyncClient;

    private final FileRepository fileRepository;
    private final EventRepository eventRepository;

    @Value("${s3.bucket.name}")
    private String bucketName;
    private final Path localFolder = Paths.get("src/main/resources/downloads");

    public Mono<FileEntity> uploadFile(FileEntity fileEntity, Long userId) {
        File file = new File(fileEntity.getLocation());
        String keyName = fileEntity.getFileName();


        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();
        return Mono.fromFuture(() -> s3AsyncClient.putObject(putObjectRequest, AsyncRequestBody.fromFile(file)))
                .then(fileRepository.save(fileEntity)
                        .doOnSuccess(savedFile -> {
                            savedFile.setStatus(FileStatus.UPLOADED);
                            savedFile.setCreatedAt(LocalDateTime.now());
                            savedFile.setUpdatedAt(LocalDateTime.now());
                            log.info("File was saved in database - {}", savedFile);
                        }))
                .flatMap(savedFile -> eventRepository.save(EventEntity.builder()
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .status(EventStatus.UPLOADED)
                                .fileId(savedFile.getId())
                                .userId(userId)
                                .build())
                        .doOnSuccess(event -> log.info("Event was saved in database - {}", event))
                        .thenReturn(savedFile))
                .doOnSuccess(f -> log.info("File uploaded - {}", f));
    }

    public Mono<FileEntity> downloadFile(FileEntity fileEntity, Long userId) {
        String keyName = fileEntity.getLocation();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();
        return Mono.fromFuture(() ->
                        s3AsyncClient.getObject(getObjectRequest, AsyncResponseTransformer.toFile(localFolder)))
                .map(unused -> {
                    fileEntity.setLocation(localFolder.toString());
                    fileEntity.setStatus(FileStatus.DOWNLOADED);
                    return fileEntity;
                }).flatMap(downloadedFile -> fileRepository
                        .save(downloadedFile)
                        .doOnSuccess(f -> log.info("File downloaded - {}", f)));
    }

}
