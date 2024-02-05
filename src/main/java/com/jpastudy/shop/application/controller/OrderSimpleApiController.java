package com.jpastudy.shop.application.controller;

import com.jpastudy.shop.domain.common.Address;
import com.jpastudy.shop.domain.order.dto.OrderSearch;
import com.jpastudy.shop.domain.order.repository.OrderSimpleQueryRepository.OrderSimpleQueryDto;
import com.jpastudy.shop.domain.order.dto.OrderStatus;
import com.jpastudy.shop.domain.order.entity.Order;
import com.jpastudy.shop.domain.order.repository.OrderRepository;
import com.jpastudy.shop.domain.order.repository.OrderSimpleQueryRepository.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;


    @GetMapping("api/v1/simple-orders")
    public List<OrderSimpleDto> ordersV1() {
        return orderRepository.findAll(new OrderSearch()).stream()
                .map(OrderSimpleDto::new)
                .toList();
    }

    @GetMapping("api/v2/simple-orders")
    public List<OrderSimpleDto> ordersV2() {
        return orderRepository.findAllWithMemberDelivery().stream()
                .map(OrderSimpleDto::new)
                .toList();
    }

    @GetMapping("api/v3/simple-orders")
    public List<OrderSimpleQueryDto> ordersV3() {
        return orderSimpleQueryRepository.findAllOrderDtos(new OrderSearch());
    }

    @Data
    public class OrderSimpleDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public OrderSimpleDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getUsername();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
