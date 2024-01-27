package service;

import dao.OrdersDao;
import dto.OrdersDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrdersService {
    private final OrdersDao ordersDao;

    public List<OrdersDto> findOrdersByCustomer(Integer customer_id) {
        return ordersDao.findOrdersByCustomer(customer_id).stream()
                .map(orders -> new OrdersDto(orders.getOrder_id(), orders.getOrder_date(), orders.getOrder_status(),
                        orders.getCustomer_id()))
                .collect(Collectors.toList());
    }

    public List<OrdersDto> findOrdersByItems(Integer item_id) {
        return ordersDao.findOrdersByItems(item_id).stream()
                .map(orders -> new OrdersDto(orders.getOrder_id(), orders.getOrder_date(), orders.getOrder_status(),
                        orders.getCustomer_id()))
                .collect(Collectors.toList());
    }
}
