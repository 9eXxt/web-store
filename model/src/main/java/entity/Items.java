package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer items_id;
    @NotNull
    @Column(unique = true)
    private String name;
    private String description;
    private BigDecimal price;
    @NotNull
    private Integer quantity_left;
}
