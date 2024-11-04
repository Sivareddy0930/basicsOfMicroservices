package com.microzz.order_service.service;

import com.microzz.order_service.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8080" , value = "USER-SERVICE")
public interface APIClient {

    @GetMapping("/api/microzz/getUser/{id}")
    public UserDto getUser(@PathVariable Long id);
}
