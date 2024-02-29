package service;

import dao.CustomerDao;
import dto.CustomerCreateDto;
import dto.CustomerReadDto;
import entity.Customer;
import entity.Role;
import lombok.RequiredArgsConstructor;
import mapper.CustomerMapper;
import mapper.CustomerReadMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.SessionUtil;
import util.ValidationUtil;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDao customerDao;
    private final CustomerMapper customerMapper;
    private final CustomerReadMapper customerReadMapper;
    private final SessionFactory sessionFactory;

    public Optional<CustomerReadDto> login(String email, String password) {
        Session session = null;
        try {
            session = SessionUtil.openSession(sessionFactory);

            return customerDao.findByEmailAndPassword(email, password)
                    .map(customerReadMapper::mapFrom);
        } finally {
            if (session != null) {
                SessionUtil.closeSession();
            }
        }
    }

    public Optional<CustomerReadDto> findByToken(String token) {
        Session session = null;
        try {
            session = SessionUtil.openSession(sessionFactory);

            return customerDao.findByToken(token)
                    .map(customerReadMapper::mapFrom);
        } finally {
            if (session != null) {
                SessionUtil.closeSession();
            }
        }
    }

    public List<CustomerReadDto> findAll() {
        Session session = null;
        try {
            session = SessionUtil.openSession(sessionFactory);

            return customerDao.findAll().stream()
                    .map(customerReadMapper::mapFrom)
                    .toList();
        } finally {
            if (session != null) {
                SessionUtil.closeSession();
            }
        }
    }

    public Optional<CustomerReadDto> findById(Integer customer_id) {
        Session session = null;
        try {
            session = SessionUtil.openSession(sessionFactory);

            return customerDao.findById(customer_id)
                    .map(customerReadMapper::mapFrom);
        } finally {
            if (session != null) {
                SessionUtil.closeSession();
            }
        }
    }

//    public Optional<CustomerReadDto> findByEmail(Integer customer_id) {
//        SessionUtil.openSession(sessionFactory);
//
//        var customerDto = customerDao.findBy(customer_id)
//                .map(customerReadMapper::mapFrom);
//
//        SessionUtil.closeSession();
//        return customerDto;
//    }

    public void createUser(CustomerCreateDto customerCreateDto) {
        create(customerCreateDto, Role.USER);
    }

    public void createAdmin(CustomerCreateDto customerCreateDto) {
        create(customerCreateDto, Role.ADMIN);
    }

    public void create(CustomerCreateDto customerCreateDto, Role role) {
        ValidationUtil.validate(customerCreateDto);

        Customer customer = customerMapper.mapFrom(customerCreateDto);
        customer.setRole(role);

        Session session = null;
        Transaction transaction = null;
        try {
            session = SessionUtil.openSession(sessionFactory);
            transaction = session.beginTransaction();

            customerDao.save(customer);

            session.getTransaction().commit();
        } catch (Exception e) {
            if(transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if(session != null) {
                SessionUtil.closeSession();
            }
        }
    }
}
