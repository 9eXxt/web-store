package dto;

import lombok.Value;

@Value
public class CreateCustomerDto {
    String first_name;
    String last_name;
    String phone_number;
    String email;
    String password;
    String address;
}
