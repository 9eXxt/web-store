package dao;

import com.querydsl.jpa.impl.JPAQuery;
import entity.UserSession;

import java.util.Optional;

import util.SessionUtil;

import static entity.QUserSession.*;

public class UserSessionDao extends CRUD<String, UserSession> {
    public UserSessionDao() {
        super(UserSession.class);
    }

    public Optional<UserSession> findToken(Integer customer_id) {
        var session = SessionUtil.getSession();
        return new JPAQuery<UserSession>(session)
                .select(userSession)
                .from(userSession)
                .where(userSession.customer.customer_id.eq(customer_id))
                .stream().findAny();
    }
//проверку ввода данных
}
