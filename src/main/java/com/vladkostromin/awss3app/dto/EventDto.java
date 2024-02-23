package com.vladkostromin.awss3app.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.vladkostromin.awss3app.entity.UserEntity;
import com.vladkostromin.awss3app.enums.EventStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventDto {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private Long fileId;
    private UserEntity user;
    private EventStatus status;
}
