package mapper;

import dto.CustomerReadDto;
import entity.Customer;
import entity.PersonalInfo;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class CustomerReadMapper implements Mapper<Customer, CustomerReadDto> {

    @Override
    public CustomerReadDto mapFrom(Customer object) {
        return new CustomerReadDto(object.getCustomer_id(),
                object.getPersonalInfo(),
                object.getEmail());
    }
}
