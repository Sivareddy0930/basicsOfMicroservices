package com.microzz.order_service.controller;

import com.microzz.order_service.dto.APIResponseDto;
import com.microzz.order_service.dto.OrderDto;
import com.microzz.order_service.service.OrderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/microzz-orders")
public class OrderController {

    @Autowired
    OrderServiceInterface  orderService;

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> getOrdersByUserId(@RequestBody OrderDto orderDto) {

       OrderDto responseOrderDto = orderService.createOrder(orderDto);

       return new ResponseEntity<>(responseOrderDto, HttpStatus.CREATED);


    }


    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<APIResponseDto> getOrdersByUserId(@PathVariable Long userId) {
        APIResponseDto apiResponseDto = orderService.getOrdersByUserId(userId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);

    }
}
