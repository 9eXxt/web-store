package com.webstore.model.dto;

import lombok.Value;

@Value
public class LoginRequestDto {
    String username;
    String password;
}
