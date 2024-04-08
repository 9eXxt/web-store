package com.webstore.model.dto;

import com.webstore.model.entity.PersonalInfo;
import lombok.Value;

@Value
public class CustomerReadDto {
    Integer customer_id;
    PersonalInfo personalInfo;
    String email;
}

// !!!!!!!!!!!!!!!!!!!! records are not working with jsp

//public record CustomerReadDto(Integer customer_id,
//                              PersonalInfo personalInfo,
//                              String email) {
//}
