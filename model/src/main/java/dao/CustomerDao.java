package dao;

import com.querydsl.jpa.impl.JPAQuery;
import entity.Customer;

import org.hibernate.Transaction;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import util.ConnectionUtil;
import java.util.List;
import java.util.Optional;


import static entity.QCustomer.customer;
import static entity.QUser_Session.*;

@AllArgsConstructor
public class CustomerDao {
    public List<Customer> findAll() {
        var connection = ConnectionUtil.getSessionFactory();
        try (var session = connection.openSession()) {
            return new JPAQuery<Customer>(session)
                    .select(customer)
                    .from(customer)
                    .fetch();
        }
    }

    public Optional<Customer> findByToken(String token) {
        var connection = ConnectionUtil.getSessionFactory();
        try (var session = connection.openSession()) {
            return new JPAQuery<Customer>(session)
                    .select(customer)
                    .from(customer)
                    .join(user_Session)
                    .on(customer.customer_id.eq(user_Session.customer.customer_id))
                    .where(user_Session.session_token.eq(token))
                    .stream().findAny();
        }
    }

    public Optional<Customer> findById(Integer customer_id) {
        var connection = ConnectionUtil.getSessionFactory();
        try (var session = connection.openSession()) {
            return new JPAQuery<Customer>(session)
                    .select(customer)
                    .from(customer)
                    .where(customer.customer_id.eq(customer_id))
                    .stream().findAny();
        }
    }

    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        var connection = ConnectionUtil.getSessionFactory();
        try (var session = connection.openSession()) {
            return new JPAQuery<Customer>(session)
                    .select(customer)
                    .from(customer)
                    .where(customer.email.eq(email)
                            .and(customer.password.eq(password)))
                    .stream().findAny();
        }
    }


    @SneakyThrows
    public boolean save(Customer customer) {
        var connection = ConnectionUtil.getSessionFactory();
        Transaction transaction = null;
        try (var session = connection.openSession()) {
            transaction = session.beginTransaction();
            if(findByEmailAndPassword(customer.getEmail(), customer.getPhone_number()).isEmpty()) {
                return false;
            }
            session.persist(customer);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Customer> findByEmailOrPhoneNumber(String email, String phone_number) {
        var connection = ConnectionUtil.getSessionFactory();
        try (var session = connection.openSession()) {
            return new JPAQuery<Customer>(session)
                    .select(customer)
                    .from(customer)
                    .where(customer.email.eq(email).or(customer.phone_number.eq(phone_number)))
                    .stream().findAny();
        }
    }
}
