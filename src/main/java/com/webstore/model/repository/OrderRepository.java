package com.webstore.model.repository;


import com.webstore.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select c, o from Order o join fetch Customer c on o.customer.customer_id = c.customer_id " +
           "where c.customer_id = :customer_id")
    List<Object[]> findOrdersByCustomer_id(Integer customer_id);

    @Query("select i, c, o from Order o join fetch OrderItem oi on o.order_id = oi.order_number.order_id " +
           "join fetch Item i on oi.item.item_id = i.item_id " +
           "join fetch Customer c on c.customer_id = o.customer.customer_id " +
           "where i.item_id = :item_id")
    List<Object[]> findOrdersByItem_id(Integer item_id);
}
