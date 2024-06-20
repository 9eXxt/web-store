package com.webstore.controller;

import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.dto.WishlistCreateDto;
import com.webstore.model.dto.WishlistReadDto;
import com.webstore.model.service.CustomerService;
import com.webstore.model.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlist")
public class WishlistController {
    private final CustomerService customerService;
    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<?> getWishlist() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String email = (String) authentication.getPrincipal();
            CustomerReadDto customerReadDto = customerService.findByEmail(email).get();
            List<WishlistReadDto> wishlistItems = wishlistService.getWishlist(customerReadDto.getCustomer_id());
            return ResponseEntity.ok(wishlistItems);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
    @PostMapping
    public ResponseEntity<?> addToWishlist(@RequestBody WishlistCreateDto wishlistCreateDto) {
        System.out.println("Received WishlistCreateDto: " + wishlistCreateDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String email = (String) authentication.getPrincipal();
            CustomerReadDto customerReadDto = customerService.findByEmail(email).get();
            wishlistService.create(wishlistCreateDto, customerReadDto.getCustomer_id());
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Integer itemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String email = (String) authentication.getPrincipal();
            CustomerReadDto customerReadDto = customerService.findByEmail(email).get();
            wishlistService.removeItemFromWishlist(customerReadDto.getCustomer_id(), itemId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
