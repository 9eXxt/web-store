package com.webstore.model.dto;

import com.webstore.model.entity.Item;
import com.webstore.model.entity.Order;
import lombok.Value;

import java.util.List;

@Value
public class ItemOrdersDto {
    ItemReadDto item;
    List<CustomerOrderDto> customerOrderList;
}
