package com.jpastudy.shop.application.usacase;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderUsacaseTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderUsacase orderUsacase;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void order() {
        // Given
        Member member = createMember("회원1", new Address("서울", "경기", "123123"));
        Item book = createBook("JPA", 1000, 10);

        int count = 2;

        // When
        Long orderId = orderUsacase.order(member.getId(), book.getId(), count);

        // Then
        Order getorder = orderRepository.find(orderId);

        assertEquals(OrderStatus.ORDER, getorder.getStatus(),
                "상품 주문시 상태는 ORDER");

        assertEquals(1, getorder.getOrderItems().size(),
                "주문한 상품 종류 수가 정확해야 한다.");

        assertEquals(book.getPrice() * 2, getorder.getTotalPrice(),
                "주문 가격은 가격 * 수량이다.");

        assertEquals(book.getStockQuantity(), 8,
                "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    void orderOverFlow() {
        // Given
        Member member = createMember("회원1", new Address("서울", "경기", "123123"));
        Item item = createBook("JPA", 1000, 10);
        int orderCount = 13;

        // Then
        assertThrows(RuntimeException.class, () -> orderUsacase.order(member.getId(), item.getId(), orderCount));
    }

    private Item createBook(String name, int price, int quantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String username, Address address) {
        Member member = new Member();
        member.setUsername(username);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}