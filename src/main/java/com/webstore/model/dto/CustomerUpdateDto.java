package com.webstore.model.dto;

import lombok.Value;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Value
public class CustomerUpdateDto {
    @Pattern(regexp = "^[A-Za-z]*$", message = "{invalid.first_name}")
    String first_name;

    @Pattern(regexp = "^[A-Za-z]*$", message = "{invalid.last_name}")
    String last_name;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^\\+\\d{10,15}$", message = "{invalid.phone_number}")
    String phone_number;

    @NotNull(message = "Email cannot be null")
    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "{invalid.email}")
    String email;

    String address;
}
