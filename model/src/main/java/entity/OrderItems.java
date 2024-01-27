package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
@AllArgsConstructor
@Data
@Builder
@ToString
public class OrderItems {
    private Integer order_items_id;
    private Integer order_number;
    private String name_item;
    private Integer count;
}
