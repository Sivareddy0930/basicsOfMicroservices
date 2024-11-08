package com.microzz.order_service.service;

import com.microzz.order_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface APIClient {

    @GetMapping("/api/users/getUser/{id}")
    public UserDto getUser(@PathVariable Long id);
}
