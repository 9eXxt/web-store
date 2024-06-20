package com.webstore.model.mapper;

import com.webstore.model.dto.WishlistReadDto;
import com.webstore.model.entity.Wishlist;
import org.springframework.stereotype.Component;

@Component
public class WishlistReadMapper implements Mapper<Wishlist, WishlistReadDto> {
    @Override
    public WishlistReadDto mapFrom(Wishlist object) {
        return new WishlistReadDto(object.getItem().getItem_id());
    }
}
