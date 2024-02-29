package mapper;

import dto.CustomerCreateDto;
import entity.Customer;
import entity.PersonalInfo;

public class CustomerMapper implements Mapper<CustomerCreateDto, Customer> {
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
