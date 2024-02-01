package com.jpastudy.shop.domain.order.entity;

import com.jpastudy.shop.domain.delivery.enitty.Delivery;
import com.jpastudy.shop.domain.member.entity.Member;
import com.jpastudy.shop.domain.order.dto.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "orders")
@SequenceGenerator(
        name = "ORDER_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ"
)
@Getter @Setter
public class Order {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ORDER_SEQ_GENERATOR"
    )
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
