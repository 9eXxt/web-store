package com.webstore.model.mapper;

import com.webstore.model.dto.WishlistCreateDto;
import com.webstore.model.entity.Item;
import com.webstore.model.entity.Wishlist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WishlistCreateMapper implements Mapper<WishlistCreateDto, Wishlist> {

    @Override
    public Wishlist mapFrom(WishlistCreateDto object) {
        Item item = Item.builder()
                .item_id(object.getItem_id())
                .build();
        return Wishlist.builder()
                .item(item)
                .build();
    }
}
