package com.jpastudy.shop.domain.order.entity;

import com.jpastudy.shop.domain.item.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@SequenceGenerator(
        name = "ORDERITEM_SEQ_GENERATOR",
        sequenceName = "ORDERITEM_SEQ"
)
@Setter @Getter
public class OrderItem {

    @Id
    @GeneratedValue(
            generator = "ORDERITEM_SEQ_GENERATOR",
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        //재고 차감
        item.removeStock(count);

        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(getCount());
    }

    public int getTotalPrice() {
        return getCount() * getOrderPrice();
    }
}
