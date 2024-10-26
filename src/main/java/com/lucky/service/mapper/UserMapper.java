package com.lucky.service.mapper;

import com.lucky.service.User;
import com.lucky.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userFromUserDTO(UserDTO userDTO);
}
