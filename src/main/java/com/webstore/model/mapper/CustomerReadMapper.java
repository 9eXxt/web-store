package com.webstore.model.mapper;

import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerReadMapper implements Mapper<Customer, CustomerReadDto> {

    @Override
    public CustomerReadDto mapFrom(Customer object) {
        return new CustomerReadDto(object.getCustomer_id(),
                object.getPersonalInfo(),
                object.getEmail());
    }
}
