package com.vladkostromin.awss3app.entity;

import com.vladkostromin.awss3app.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;


@Table("events")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class EventEntity extends BaseEntity{

    private Long userId;
    private Long fileId;
    @Transient
    private UserEntity user;

    private EventStatus status;
}
