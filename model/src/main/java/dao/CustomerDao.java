package dao;

import entity.Customer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import mapper.CustomerMapper;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@AllArgsConstructor
public class CustomerDao {
    private final CustomerMapper customerMapper;
    private static final String FIND_ALL = """
            SELECT *
            FROM customer;
            """;
    private static final String SAVE_SQL = """
            INSERT INTO customer (first_name, last_name, phone_number, email, password, address)
            VALUES (?,?,?,?,?,?);
            """;
    private static final String FIND_BY_EMAIL_AND_PASSWORD = """
            SELECT *
            FROM customer
            WHERE email = ?
            AND password = ?;
            """;
    private static final String FIND_BY_ID = """
            SELECT *
            FROM customer
            WHERE customer_id = ?;
            """;
    private static final String FIND_BY_TOKEN = """
            SELECT customer.*
            FROM user_sessions
            JOIN customer USING (customer_id)
            WHERE session_token = ?""";

    @SneakyThrows
    public List<Customer> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Customer> customerList = new ArrayList<>();
            while (resultSet.next()) {
                customerList.add(customerMapper.buildEntity(resultSet));
            }
            return customerList;
        }
    }

    @SneakyThrows
    public Optional<Customer> findByToken(String token) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_TOKEN)) {
            preparedStatement.setString(1, token);

            ResultSet resultSet = preparedStatement.executeQuery();
            Customer customer = null;
            if (resultSet.next()) {
                customer = customerMapper.buildEntity(resultSet);
            }
            return Optional.ofNullable(customer);
        }
    }

    @SneakyThrows
    public Optional<Customer> findById(Integer customer_id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setInt(1, customer_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            Customer customer = null;
            if (resultSet.next()) {
                customer = customerMapper.buildEntity(resultSet);
            }
            return Optional.ofNullable(customer);
        }
    }

    @SneakyThrows
    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            Customer customer = null;
            if (resultSet.next()) {
                customer = customerMapper.buildEntity(resultSet);
            }
            return Optional.ofNullable(customer);
        }
    }

    @SneakyThrows
    public void save(Customer customer) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, customer.getFirst_name());
            preparedStatement.setObject(2, customer.getLast_name());
            preparedStatement.setObject(3, customer.getPhone_number());
            preparedStatement.setObject(4, customer.getEmail());
            preparedStatement.setObject(5, customer.getPassword());
            preparedStatement.setObject(6, customer.getAddress());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            customer.setCustomer_id(generatedKeys.getObject("customer_id", Integer.class));

        }
    }
}
