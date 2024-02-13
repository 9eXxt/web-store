package mapper;

import dto.CreateCustomerDto;
import entity.Customer;
import entity.PersonalInfo;

public class CreateCustomerMapper implements Mapper<CreateCustomerDto, Customer> {
    @Override
    public Customer mapFrom(CreateCustomerDto object) {
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
