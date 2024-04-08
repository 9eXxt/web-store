package com.webstore.model.repository;


import com.querydsl.jpa.impl.JPAQuery;
import com.webstore.model.entity.UserSession;
import com.webstore.model.util.SessionUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.webstore.model.entity.QUserSession.userSession;
@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, String> {
    @Query("select u from UserSession u where u.customer.customer_id = :customer_id")
    Optional<UserSession> findToken(Integer customer_id);
//проверку ввода данных
}
