package com.webstore.model.mapper;

import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerReadMapper implements Mapper<Customer, CustomerReadDto> {

    @Override
    public CustomerReadDto mapFrom(Customer object) {
        return new CustomerReadDto(object.getCustomer_id(),
                object.getPersonalInfo().getFirst_name(),
                object.getPersonalInfo().getLast_name(),
                object.getEmail(),
                object.getPhone_number(),
                object.getPersonalInfo().getAddress());
    }
}
