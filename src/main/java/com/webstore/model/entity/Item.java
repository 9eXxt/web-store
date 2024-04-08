package com.webstore.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "name")
@ToString(exclude = "orderItem")
@Entity
public class Item implements Manageable<OrderItem> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer item_id;
    @NotNull
    @Column(unique = true)
    private String name;
    private String description;
    private BigDecimal price;
    @NotNull
    private Integer quantity_left;
    @Builder.Default
    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItem = new ArrayList<>();

    @Override
    public void add(OrderItem entity) {
        orderItem.add(entity);
        entity.setItem(this);
    }
}
