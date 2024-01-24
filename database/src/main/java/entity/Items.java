package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@ToString
public class Items {
    private Integer items_id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity_left;
}
