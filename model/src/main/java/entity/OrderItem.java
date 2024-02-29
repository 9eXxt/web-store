package entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"order_number", "item"})
@EqualsAndHashCode(exclude = {"order_number", "item"})
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_item_id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order_number;
    private String name_item;
    private Integer count;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
}
