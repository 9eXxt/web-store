package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@Builder
@ToString
public class Orders {
    private Integer order_id;
    private LocalDateTime order_date;
    private LocalDateTime close_order_date;
    private String order_status;
    private Integer customer_id;
}
