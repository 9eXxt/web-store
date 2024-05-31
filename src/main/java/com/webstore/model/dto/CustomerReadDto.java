package com.webstore.model.dto;

import com.webstore.model.entity.PersonalInfo;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class CustomerReadDto {
    Integer customer_id;
    String first_name;
    String last_name;
    String email;
    String phone_number;
    String address;
}

// !!!!!!!!!!!!!!!!!!!! records are not working with jsp

//public record CustomerReadDto(Integer customer_id,
//                              PersonalInfo personalInfo,
//                              String email) {
//}
