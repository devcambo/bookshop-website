package com.devcambo.springinit.mapper;

import com.devcambo.springinit.model.dto.request.RegisterDto;
import com.devcambo.springinit.model.dto.request.UserCreationDto;
import com.devcambo.springinit.model.dto.request.UserUpdateDto;
import com.devcambo.springinit.model.dto.response.UserResponseDto;
import com.devcambo.springinit.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserResponseDto toDto(User user);

  User toEntity(UserCreationDto userCreationDto);

  @Mappings(
    {
      @Mapping(target = "userId", ignore = true),
      @Mapping(target = "roles", ignore = true),
      @Mapping(target = "password", ignore = true),
    }
  )
  void updateFromDto(UserUpdateDto dto, @MappingTarget User entity);

  User toEntity(RegisterDto registerDto);
}
