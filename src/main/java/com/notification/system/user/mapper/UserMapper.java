package com.notification.system.user.mapper;

import com.notification.system.user.dto.UserRequestDTO;
import com.notification.system.user.dto.UserResponseDTO;
import com.notification.system.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "password",ignore = true)
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toResponse(User user);

}
