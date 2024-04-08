package com.webstore.model.dto;

import com.webstore.model.entity.Customer;
import com.webstore.model.entity.Order;
import lombok.Value;

import java.util.List;

@Value
public class CustomerOrdersDto {
    CustomerReadDto customer;
    List<OrderReadDto> ordersList;
}
