package mapper;

import entity.Customer;
import entity.PersonalInfo;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class CustomerMapper implements EntityMapper<Customer> {
    @SneakyThrows
    @Override
    public Customer buildEntity(ResultSet resultSet) {
        PersonalInfo personalInfo = PersonalInfo.builder()
                .first_name(resultSet.getString("first_name"))
                .last_name(resultSet.getString("last_name"))
                .address(resultSet.getString("address"))
                .build();
        return Customer.builder()
                .customer_id(resultSet.getInt("customer_id"))
                .personalInfo(personalInfo)
                .phone_number(resultSet.getString("phone_number"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .build();
    }
}
