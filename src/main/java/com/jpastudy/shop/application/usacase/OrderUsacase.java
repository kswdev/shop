package com.jpastudy.shop.application.usacase;

import com.jpastudy.shop.domain.delivery.enitty.Delivery;
import com.jpastudy.shop.domain.item.entity.Item;
import com.jpastudy.shop.domain.item.repository.ItemRepository;
import com.jpastudy.shop.domain.member.entity.Member;
import com.jpastudy.shop.domain.member.repository.MemberRepository;
import com.jpastudy.shop.domain.order.entity.Order;
import com.jpastudy.shop.domain.order.entity.OrderItem;
import com.jpastudy.shop.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderUsacase {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.find(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);

        return order.getId();
    }
}
