package com.vladkostromin.awss3app.mapper;

import com.vladkostromin.awss3app.dto.UserDto;
import com.vladkostromin.awss3app.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity map(UserDto userDto);

}
