package com.microzz.order_service.service.impl;

import com.microzz.order_service.dto.APIResponseDto;
import com.microzz.order_service.dto.OrderDto;
import com.microzz.order_service.dto.UserDto;
import com.microzz.order_service.exception.UserNotExistWithIdException;
import com.microzz.order_service.model.Order;
import com.microzz.order_service.repository.OrderRepository;
import com.microzz.order_service.service.APIClient;
import com.microzz.order_service.service.OrderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderServiceInterface {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private APIClient apiClient;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        Order order =new Order();

        order.setUserId(orderDto.getUserId());
        order.setProduct(orderDto.getProduct());
        order.setQuantity(orderDto.getQuantity());

        Order savedOrder = orderRepository.save(order);

        return new OrderDto(savedOrder.getId(), savedOrder.getUserId(), savedOrder.getProduct(), savedOrder.getQuantity());

    }

    @Override
    public APIResponseDto getOrdersByUserId(Long userId) {
        List<Order> ordersList = orderRepository.findAllByUserId(userId)
                .orElseThrow(() -> new UserNotExistWithIdException("User does not exist with id " + userId));

        List<OrderDto> listOfOrders = new ArrayList<>();

        for (Order order : ordersList) {
            OrderDto orderDto = new OrderDto(order.getId(), order.getUserId(),order.getProduct(),order.getQuantity());
            listOfOrders.add(orderDto);
        }

        UserDto userDto = apiClient.getUser(userId);

        APIResponseDto apiResponseDto = new APIResponseDto( userDto,listOfOrders);

        return apiResponseDto;
    }
}
