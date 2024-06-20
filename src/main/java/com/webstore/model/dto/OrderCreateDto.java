package com.webstore.model.dto;

import com.webstore.model.entity.OrderStatus;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class OrderCreateDto {
    List<OrderItemCreateDto> orderItems;
    BigDecimal totalPrice;
    LocalDateTime orderDate;
    Integer customerId;
    OrderStatus orderStatus;
}
