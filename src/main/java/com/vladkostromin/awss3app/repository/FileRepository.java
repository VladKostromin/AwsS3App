package com.vladkostromin.awss3app.repository;

import com.vladkostromin.awss3app.entity.FileEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface FileRepository extends R2dbcRepository<FileEntity, Long> {
}
