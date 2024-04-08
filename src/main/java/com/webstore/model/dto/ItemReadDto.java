package com.webstore.model.dto;

import lombok.Value;

@Value
public class ItemReadDto {
    Integer item_id;
    String name;
    String description;
    Integer quantity_left;
}
//public record ItemReadDto(Integer item_id,
//                          String name,
//                          String description,
//                          Integer quantity_left) {
//
//}
// !!!!!!!!!!!!!!!!!!!! records are not working with jsp
