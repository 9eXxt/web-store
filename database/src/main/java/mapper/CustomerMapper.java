package mapper;

import entity.Customer;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class CustomerMapper implements EntityMapper<Customer> {
    @SneakyThrows
    @Override
    public Customer buildEntity(ResultSet resultSet) {
        return Customer.builder()
                .customer_id(resultSet.getInt("customer_id"))
                .first_name(resultSet.getString("first_name"))
                .last_name(resultSet.getString("last_name"))
                .phone_number(resultSet.getString("phone_number"))
                .address(resultSet.getString("address"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .build();
    }
}
