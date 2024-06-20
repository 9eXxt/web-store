package com.webstore.model.mapper;

import com.webstore.model.dto.OrderItemCreateDto;
import com.webstore.model.entity.Item;
import com.webstore.model.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemCreateMapper implements Mapper<OrderItemCreateDto, OrderItem>{
    @Override
    public OrderItem mapFrom(OrderItemCreateDto object) {
        Item item = Item.builder()
                .item_id(object.getItem_id())
                .build();
        return OrderItem.builder()
                .name_item(object.getName_item())
                .count(object.getCount())
                .item(item)
                .build();
    }
}
