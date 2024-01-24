package dto;


import lombok.Value;

import java.time.LocalDateTime;

@Value
public class OrdersDto {
    Integer order_id;
    LocalDateTime order_date;
    String order_status;
    Integer customer_id;
}
