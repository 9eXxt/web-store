package com.webstore.model.repository;

import com.webstore.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("select c from Customer c join fetch UserSession u on c.customer_id = u.customer.customer_id where u.session_token = :token")
    Optional<Customer> findByToken(String token);
    Optional<Customer> findByEmailAndPassword(String email, String password);
    Optional<Customer> findByEmail(String email);
    @Query("select c from Customer c join fetch Order o on c.customer_id = o.customer.customer_id " +
           "join fetch OrderItem oi on oi.order_number.order_id = o.order_id " +
           "join fetch Item i on i.item_id = oi.item.item_id " +
           "where i.item_id = :item_id")
    List<Customer> findCustomersByItem_id(Integer item_id);
   // Optional<Customer> findByEmailOrphone_number(String email, String phone_number);
}
