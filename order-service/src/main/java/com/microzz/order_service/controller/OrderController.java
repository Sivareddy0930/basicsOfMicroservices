package com.microzz.order_service.controller;

import com.microzz.order_service.dto.APIResponseDto;
import com.microzz.order_service.dto.OrderDto;
import com.microzz.order_service.dto.UserDto;
import com.microzz.order_service.exception.ServerUnavailableException;
import com.microzz.order_service.service.OrderServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderServiceInterface  orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> getOrdersByUserId(@RequestBody OrderDto orderDto) {

       OrderDto responseOrderDto = orderService.createOrder(orderDto);

       return new ResponseEntity<>(responseOrderDto, HttpStatus.CREATED);

    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<APIResponseDto> getOrdersByUserId(@PathVariable Long userId) {
        try {
            APIResponseDto apiResponseDto = orderService.getOrdersByUserId(userId);
            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
        } catch (WebClientResponseException ex) {
            LOGGER.error("WebClient error: {}", ex.getMessage());
            return new ResponseEntity<>(new APIResponseDto(new UserDto(userId, "Service Unavailable", ""), new ArrayList<>()),
                    HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch (ServerUnavailableException ex) {
            LOGGER.error("Server error: {}", ex.getMessage());
            return new ResponseEntity<>(new APIResponseDto(new UserDto(userId, "Service Unavailable", ""), new ArrayList<>()),
                    HttpStatus.SERVICE_UNAVAILABLE);
        }catch (Exception ex) {
            log.error("Unexpected error: {}", ex.getMessage());
            return new ResponseEntity<>(new APIResponseDto(new UserDto(userId, "Unexpected Error", ""), new ArrayList<>()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
