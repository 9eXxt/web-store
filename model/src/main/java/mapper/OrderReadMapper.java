package mapper;

import dto.OrderReadDto;
import entity.Order;

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
