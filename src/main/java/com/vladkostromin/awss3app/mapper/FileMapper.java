package com.vladkostromin.awss3app.mapper;

import com.vladkostromin.awss3app.dto.FileDto;
import com.vladkostromin.awss3app.entity.FileEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileDto map(FileEntity file);

    @InheritInverseConfiguration
    FileEntity map(FileDto dto);
}
