package com.microzz.user_service.service.impl;

import com.microzz.user_service.dto.UserDto;
import com.microzz.user_service.exception.UserNotExistWithIdException;
import com.microzz.user_service.model.User;
import com.microzz.user_service.repository.UserRepository;
import com.microzz.user_service.service.UserServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
//        if (userDto == null) {
//            throw new UserDataShouldNotBeNullException("User Data cannot be null");
//        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        logger.info("saved successfully");

        User savedUser = userRepository.save(user);

        UserDto savedUserDto = new UserDto();
        savedUserDto.setId(savedUser.getId());
        savedUserDto.setName(savedUser.getName());
        savedUserDto.setEmail(savedUser.getEmail());

        return savedUserDto;

    }

    @Override
    public UserDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotExistWithIdException("User does not exist with id " + id));

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;

    }
}
