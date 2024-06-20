package com.webstore.model.repository;

import com.webstore.model.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    @Query("SELECT w FROM Wishlist w WHERE w.customer.customer_id = :customer_id")
    List<Wishlist> findByCustomerId(Integer customer_id);

    @Query("SELECT w FROM Wishlist w WHERE w.customer.customer_id = :customer_id AND w.item.item_id = :itemId")
    Wishlist findByCustomerIdAndItemId(Integer customer_id, Integer itemId);
}
