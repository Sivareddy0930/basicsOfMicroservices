package com.microzz.user_service.service;

import com.microzz.user_service.dto.UserDto;

public interface UserServiceInterface {
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Long id);
}
