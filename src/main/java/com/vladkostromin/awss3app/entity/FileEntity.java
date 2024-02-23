package com.vladkostromin.awss3app.entity;

import com.vladkostromin.awss3app.enums.FileStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("files")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class FileEntity extends BaseEntity {

    private String fileName;
    private String location;
    private FileStatus status;
}
