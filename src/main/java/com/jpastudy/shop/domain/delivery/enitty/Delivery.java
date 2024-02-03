package com.jpastudy.shop.domain.delivery.enitty;

import com.jpastudy.shop.domain.common.Address;
import com.jpastudy.shop.domain.delivery.dto.DeliveryStatus;
import com.jpastudy.shop.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@SequenceGenerator(
        name = "DELIVERY_SEQ_GENERATOR",
        sequenceName = "DELIVERY_SEQ"
)
@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "DELIVERY_SEQ_GENERATOR"
    )
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
