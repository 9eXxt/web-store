package com.webstore.model.mapper;


import com.webstore.model.dto.CustomerCreateDto;
import com.webstore.model.entity.Customer;
import com.webstore.model.entity.PersonalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerCreateMapper implements Mapper<CustomerCreateDto, Customer> {
    @Override
    public Customer mapFrom(CustomerCreateDto object) {
        PersonalInfo personalInfo = PersonalInfo.builder()
                .first_name(object.getFirst_name())
                .last_name(object.getLast_name())
                .address(object.getAddress())
                .build();
        return Customer.builder()
                .personalInfo(personalInfo)
                .phone_number(object.getPhone_number())
                .email(object.getEmail())
                .password(object.getPassword())
                .build();
    }
}
