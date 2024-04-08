package com.webstore.model.dto;

import lombok.Value;

@Value
public class CustomerOrderDto {
    CustomerReadDto customerReadDto;
    OrderReadDto orderReadDto;
}
