package com.microzz.order_service.service.impl;

import com.microzz.order_service.dto.APIResponseDto;
import com.microzz.order_service.dto.OrderDto;
import com.microzz.order_service.dto.UserDto;
import com.microzz.order_service.exception.ServerUnavailableException;
import com.microzz.order_service.exception.UserNotExistWithIdException;
import com.microzz.order_service.model.Order;
import com.microzz.order_service.repository.OrderRepository;
import com.microzz.order_service.service.OrderServiceInterface;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderServiceInterface {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    private OrderRepository orderRepository;

//    @Autowired
//    private APIClient apiClient;


    private WebClient.Builder webClientBuilder;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        Order order =new Order();

        order.setUserId(orderDto.getUserId());
        order.setProduct(orderDto.getProduct());
        order.setQuantity(orderDto.getQuantity());

        Order savedOrder = orderRepository.save(order);

        return new OrderDto(savedOrder.getId(), savedOrder.getUserId(), savedOrder.getProduct(), savedOrder.getQuantity());

    }

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "userFallback")
    @Retry(name = "${spring.application.name}")
    @Override
    public APIResponseDto getOrdersByUserId(Long userId) {
        List<Order> ordersList = orderRepository.findAllByUserId(userId)
                .orElseThrow(() -> new UserNotExistWithIdException("User does not exist with id " + userId));

        List<OrderDto> listOfOrders = new ArrayList<>();
        for (Order order : ordersList) {
            listOfOrders.add(new OrderDto(order.getId(), order.getUserId(), order.getProduct(), order.getQuantity()));
        }
        UserDto userDto = null;
       try {
            userDto = webClientBuilder.build()
                   .get()
                   .uri("http://user-service/api/users/getUser/" + userId)
                   .retrieve()
                   .onStatus(
                           status -> status.is5xxServerError(),
                           response -> {
                               log.warn("Received 5xx error from user-service: {}", response.statusCode());
                               return Mono.error(new WebClientResponseException(
                                       "Service Unavailable", response.statusCode().value(), "Service Unavailable", null, null, null));
                           })
                   .bodyToMono(UserDto.class)
                   .block();
       }
       catch (WebClientResponseException e) {
           throw new ServerUnavailableException(e.getMessage());
       }

        return new APIResponseDto(userDto, listOfOrders);
    }


//    public APIResponseDto userFallback(Long userId, Throwable t) {
//        log.info("Fallback executed due to: " + t.getMessage());
//
//        // Create fallback response
//        UserDto fallbackUser = new UserDto(userId, "Fallback mechanism for User", "user@example.com");
//        List<OrderDto> listOfOrders = new ArrayList<>();
//        OrderDto orderDto = new OrderDto(0L, 0L, "fallback", 0);
//        listOfOrders.add(orderDto);
//
//        APIResponseDto fallbackResponse = new APIResponseDto(fallbackUser, listOfOrders);
//
//        // Throw exception with fallback response
//        throw new ServerUnavailableException("user-service unavailable", fallbackResponse);
//    }

    public APIResponseDto userFallback(Long userId, Throwable t) {
        log.info("Fallback executed due to: " + t.getMessage());

        // Create fallback response without throwing an exception
        UserDto fallbackUser = new UserDto(userId, "Fallback User", "user@example.com");
        List<OrderDto> listOfOrders = List.of(new OrderDto(0L, 0L, "Fallback Product", 0));

        return new APIResponseDto(fallbackUser, listOfOrders);
    }


}
