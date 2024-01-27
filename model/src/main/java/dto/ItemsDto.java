package dto;

import lombok.Value;
@Value
public class ItemsDto {
    Integer item_id;
    String name;
    Integer quantity_left;
}
