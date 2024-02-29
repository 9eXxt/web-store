package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"customer", "orderItem"})
@EqualsAndHashCode(exclude = {"customer", "orderItem"})
@Entity
public class Order implements Manageable<OrderItem> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_id;
    @NotNull
    private LocalDateTime order_date;
    private LocalDateTime close_order_date;
    private String order_status;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Builder.Default
    @OneToMany(mappedBy = "order_number", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItem = new ArrayList<>();

    @Override
    public void add(OrderItem entity) {
        orderItem.add(entity);
        entity.setOrder_number(this);
    }
}
