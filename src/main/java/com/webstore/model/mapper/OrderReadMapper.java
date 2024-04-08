package com.webstore.model.mapper;


import com.webstore.model.dto.OrderReadDto;
import com.webstore.model.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderReadMapper implements Mapper<Order, OrderReadDto>{
    @Override
    public OrderReadDto mapFrom(Order object) {
        return new OrderReadDto(
                object.getOrder_id(),
                object.getOrder_date(),
                object.getOrder_status(),
                object.getCustomer().getCustomer_id()
        );
    }
}
