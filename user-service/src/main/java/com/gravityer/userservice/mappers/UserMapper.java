package com.gravityer.userservice.mappers;

import com.gravityer.userservice.dtos.UserDto;
import com.gravityer.userservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public UserDto toUserDto(User user);

    @Mapping(target = "id", ignore = true)
    public User toUser(UserDto userDto);
}
