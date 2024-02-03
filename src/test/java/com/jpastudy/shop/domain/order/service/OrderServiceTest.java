package com.jpastudy.shop.domain.order.service;

import com.jpastudy.shop.application.usacase.OrderUsacase;
import com.jpastudy.shop.domain.common.Address;
import com.jpastudy.shop.domain.item.entity.Book;
import com.jpastudy.shop.domain.item.entity.Item;
import com.jpastudy.shop.domain.member.entity.Member;
import com.jpastudy.shop.domain.order.dto.OrderStatus;
import com.jpastudy.shop.domain.order.entity.Order;
import com.jpastudy.shop.domain.order.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderUsacase orderUsacase;
    @Autowired OrderRepository orderRepository;
    @Autowired EntityManager em;


    @Test
    void orderCancel() {

        // Given
        Member member = createMember("회원1", new Address("서울", "경기", "123123"));
        Item item = createBook("JPA", 1000, 10);

        int orderCount = 2;

        Long orderId = orderUsacase.order(member.getId(), item.getId(), orderCount);

        // When
        orderService.cancelOrder(orderId);

        // Then
        Order getOrder = orderRepository.find(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(),
                "주문 취소시 상태는 CANCEL");
        assertEquals(10, item.getStockQuantity(),
                "주문이 취소된 상품은 재고가 원복되어야 한다.");
    }

    private Member createMember(String username, Address address) {
        Member member = new Member();
        member.setUsername(username);
        member.setAddress(address);
        em.persist(member);
        return member;
    }

    private Item createBook(String name, int price, int quantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }
}