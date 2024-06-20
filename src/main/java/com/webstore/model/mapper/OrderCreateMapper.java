package com.webstore.model.mapper;

import com.webstore.model.dto.OrderCreateDto;
import com.webstore.model.dto.OrderItemCreateDto;
import com.webstore.model.entity.Order;
import com.webstore.model.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCreateMapper implements Mapper<OrderCreateDto, Order> {
    @Autowired
    private OrderItemCreateMapper orderItemCreateMapper;

    @Override
    public Order mapFrom(OrderCreateDto object) {
        Order order = Order.builder()
                .order_date(object.getOrderDate())
                .total_price(object.getTotalPrice())
                .build();
        for (OrderItemCreateDto itemDto : object.getOrderItems()) {
            OrderItem orderItem = orderItemCreateMapper.mapFrom(itemDto);
            orderItem.setOrder_number(order);
            order.add(orderItem);
        }
        return order;
    }
}
