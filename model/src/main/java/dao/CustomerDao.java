package dao;

import com.querydsl.jpa.impl.JPAQuery;
import entity.Customer;
import util.SessionUtil;


import java.util.Optional;

import static entity.QCustomer.customer;
import static entity.QUserSession.*;


public class CustomerDao extends CRUD<Integer, Customer> {
    public CustomerDao() {
        super(Customer.class);
    }

    public Optional<Customer> findByToken(String token) {
        var session = SessionUtil.getSession();
        return new JPAQuery<Customer>(session)
                .select(customer)
                .from(customer)
                .join(userSession)
                .on(customer.customer_id.eq(userSession.customer.customer_id))
                .where(userSession.session_token.eq(token))
                .stream().findAny();
    }

    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        var session = SessionUtil.getSession();
        return new JPAQuery<Customer>(session)
                .select(customer)
                .from(customer)
                .where(customer.email.eq(email)
                        .and(customer.password.eq(password)))
                .stream().findAny();
    }

    public Optional<Customer> findByEmailOrPhoneNumber(String email, String phone_number) {
        var session = SessionUtil.getSession();
        return new JPAQuery<Customer>(session)
                .select(customer)
                .from(customer)
                .where(customer.email.eq(email).or(customer.phone_number.eq(phone_number)))
                .stream().findAny();
    }
    //    @SneakyThrows
//    public boolean save(Customer customer) {
//        var connection = ConnectionUtil.sessionFactory();
//        Transaction transaction = null;
//        try (var session = connection.openSession()) {
//            transaction = session.beginTransaction();
//            if (findByEmailOrPhoneNumber(customer.getEmail(), customer.getPhone_number()).isPresent()) {
//                return false;
//            }
//            session.persist(customer);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            transaction.rollback();
//            e.printStackTrace();
//            return false;
//        }
//    }
}
