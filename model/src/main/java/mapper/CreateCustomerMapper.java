package mapper;

import dto.CreateCustomerDto;
import entity.Customer;

public class CreateCustomerMapper implements Mapper<CreateCustomerDto, Customer> {
    @Override
    public Customer mapFrom(CreateCustomerDto object) {
        return Customer.builder()
                .first_name(object.getFirst_name())
                .last_name(object.getLast_name())
                .phone_number(object.getPhone_number())
                .email(object.getEmail())
                .address(object.getAddress())
                .password(object.getPassword())
                .build();
    }
}
