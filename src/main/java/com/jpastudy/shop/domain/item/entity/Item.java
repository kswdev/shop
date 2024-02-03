package com.jpastudy.shop.domain.item.entity;

import com.jpastudy.shop.domain.category.entity.Category;
import com.jpastudy.shop.domain.order.entity.OrderItem;
import com.jpastudy.shop.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@SequenceGenerator(
        name = "ITEM_SEQ_GENERATOR",
        sequenceName = "ITEM_SEQ"
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ITEM_SEQ_GENERATOR"
    )
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories;

    //==비즈니스 로직==//
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0)
            throw new NotEnoughStockException("need more stocks");

        this.stockQuantity = restStock;
    }
}
