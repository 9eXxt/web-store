package com.webstore.model.repository;


import com.webstore.model.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, String> {
    @Query("select u from UserSession u where u.customer.customer_id = :customer_id")
    Optional<UserSession> findToken(Integer customer_id);
//проверку ввода данных
}
