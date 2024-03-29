package com.jpastudy.shop.domain.item.repository;

import com.jpastudy.shop.domain.item.entity.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            System.out.println("null");
            em.persist(item);
        } else {
            System.out.println("merge");
            em.merge(item);
        }
    }

    public Item find(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM Item i", Item.class)
                .getResultList();
    }
}
