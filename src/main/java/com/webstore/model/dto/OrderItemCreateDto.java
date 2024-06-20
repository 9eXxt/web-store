package com.webstore.model.dto;

import lombok.Value;

@Value
public class OrderItemCreateDto {
    Integer item_id;
    String name_item;
    Integer count;
}
