package org.example.mapper;

import org.example.entity.UserEntity;
import org.example.model.UserRequest;
import org.example.model.UserResponse;

public class UserMapper {
    public static UserResponse userRequestDtoToUserResponseDto(UserRequest user) {
        return new UserResponse(
                user.getId(),
                user.getCnp(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public static UserResponse userEntityToUserResponseDto(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getId(),
                userEntity.getCnp(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }

    public static UserEntity userRequestToUserEntity(UserRequest user) {
        return new UserEntity(
                user.getId(),
                user.getCnp(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
