package com.jpastudy.shop.domain.order.service;

import com.jpastudy.shop.domain.order.dto.OrderSearch;
import com.jpastudy.shop.domain.order.entity.Order;
import com.jpastudy.shop.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Long save(Order order) {
        orderRepository.save(order);
        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId) {

        //주문 조회
        Order order = orderRepository.findById(orderId);

        //주문 취소
        order.cancel();
    }

    //조회
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
