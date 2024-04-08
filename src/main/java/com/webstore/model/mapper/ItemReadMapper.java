package com.webstore.model.mapper;


import com.webstore.model.dto.ItemReadDto;
import com.webstore.model.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemReadMapper implements Mapper<Item, ItemReadDto>{
    @Override
    public ItemReadDto mapFrom(Item object) {
        return new ItemReadDto(
                object.getItem_id(),
                object.getName(),
                object.getDescription(),
                object.getQuantity_left()
        );
    }
}
