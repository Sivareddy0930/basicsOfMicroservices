package com.microzz.order_service.service;

import com.microzz.order_service.dto.APIResponseDto;
import com.microzz.order_service.dto.OrderDto;

import java.util.List;

public interface OrderServiceInterface {
    OrderDto createOrder(OrderDto orderDto);
    APIResponseDto getOrdersByUserId(Long userId);
}
