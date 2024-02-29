package mapper;

import dto.ItemReadDto;
import entity.Item;

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
