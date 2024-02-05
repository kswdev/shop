package com.jpastudy.shop.domain.order.repository.OrderSimpleQueryRepository;

import com.jpastudy.shop.domain.common.Address;
import com.jpastudy.shop.domain.order.dto.OrderStatus;
import com.jpastudy.shop.domain.order.entity.Order;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String username, LocalDateTime orderDate, OrderStatus status, Address address) {
        this.orderId = orderId;
        this.name = username;
        this.orderDate = orderDate;
        this.orderStatus = status;
        this.address = address;
    }
}
