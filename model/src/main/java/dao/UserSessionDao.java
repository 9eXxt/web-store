package dao;

import com.querydsl.jpa.impl.JPAQuery;
import entity.User_Session;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import util.ConnectionUtil;

import java.util.Optional;

import org.hibernate.Transaction;

import static entity.QUser_Session.*;

@AllArgsConstructor
public class UserSessionDao {
    public Optional<User_Session> findToken(Integer customer_id) {
        var connection = ConnectionUtil.getSessionFactory();
        try (var session = connection.openSession()) {
            return new JPAQuery<User_Session>(session)
                    .select(user_Session)
                    .from(user_Session)
                    .where(user_Session.customer.customer_id.eq(customer_id))
                    .stream().findAny();
        }
    }
//проверку ввода данных
    @SneakyThrows
    public void createSession(User_Session userSession) {
        var connection = ConnectionUtil.getSessionFactory();
        try (var session = connection.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(userSession);
            transaction.commit();
        }
    }
}
