package com.jpastudy.shop.application.controller;

import com.jpastudy.shop.domain.common.Address;
import com.jpastudy.shop.domain.order.dto.OrderSearch;
import com.jpastudy.shop.domain.order.dto.OrderStatus;
import com.jpastudy.shop.domain.order.entity.Order;
import com.jpastudy.shop.domain.order.entity.OrderItem;
import com.jpastudy.shop.domain.order.repository.OrderRepository;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<OrderDto> ordersV1() {
        return orderRepository.findAll(new OrderSearch()).stream()
                .map(OrderDto::new)
                .toList();
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        return orderRepository.findAllWithItem().stream()
                .map(OrderDto::new)
                .toList();
    }

    @GetMapping("/api/v2.1/orders")
    public List<OrderDto> ordersV2_page(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit
    ) {
        return orderRepository.findAllWithMemberDelivery(offset, limit).stream()
                .map(OrderDto::new)
                .toList();
    }

    @Getter
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> OrderItemDto;

        public OrderDto(Order o) {
            this.orderId = o.getId();
            this.name = o.getMember().getUsername();
            this.orderDate = o.getOrderDate();
            this.orderStatus = o.getStatus();
            this.address = o.getDelivery().getAddress();
            this.OrderItemDto = o.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .toList();
        }
    }

    @Getter
    static class OrderItemDto {

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
