package com.microzz.user_service.controller;

import com.microzz.user_service.dto.UserDto;
import com.microzz.user_service.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserServiceInterface userServiceInterface;

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto userDtoResponse = userServiceInterface.createUser(userDto);

        return new ResponseEntity<>(userDtoResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        UserDto userDtoResponse = userServiceInterface.getUserById(id);
        return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }
}
