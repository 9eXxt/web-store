package com.webstore.model.service;

import com.webstore.model.dto.WishlistCreateDto;
import com.webstore.model.dto.WishlistReadDto;
import com.webstore.model.entity.Customer;
import com.webstore.model.entity.Wishlist;
import com.webstore.model.mapper.WishlistCreateMapper;
import com.webstore.model.mapper.WishlistReadMapper;
import com.webstore.model.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final WishlistCreateMapper wishlistCreateMapper;
    private final WishlistReadMapper wishlistReadMapper;

    public List<WishlistReadDto> getWishlist(Integer customer_id) {
        return wishlistRepository.findByCustomerId(customer_id).stream()
                .map(wishlistReadMapper::mapFrom)
                .toList();
    }
    @Transactional
    public void removeItemFromWishlist(Integer customerId, Integer itemId) {
        Wishlist wishlistItem = wishlistRepository.findByCustomerIdAndItemId(customerId, itemId);
        if (wishlistItem != null) {
            wishlistRepository.delete(wishlistItem);
        }
    }
    @Transactional
    public void create(WishlistCreateDto wishlistCreateDto, Integer customer_id) {
        Customer customer = Customer.builder()
                .customer_id(customer_id)
                .build();

        Wishlist wishlistItem = wishlistCreateMapper.mapFrom(wishlistCreateDto);
        wishlistItem.setCustomer(customer);

        wishlistRepository.save(wishlistItem);
    }
}
