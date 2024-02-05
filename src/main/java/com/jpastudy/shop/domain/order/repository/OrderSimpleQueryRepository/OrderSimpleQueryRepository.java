package com.jpastudy.shop.domain.order.repository.OrderSimpleQueryRepository;

import com.jpastudy.shop.domain.order.dto.OrderSearch;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findAllOrderDtos(OrderSearch orderSearch) {
        return em.createQuery("select new com.jpastudy.shop.domain.order.repository.OrderSimpleQueryRepository.OrderSimpleQueryDto(o.id, m.username, o.orderDate, o.status, d.address)" +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d", OrderSimpleQueryDto.class
        ).getResultList();
    }
}
