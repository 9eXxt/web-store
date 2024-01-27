package service;

import dao.OrdersDao;
import dto.OrdersDto;
import entity.Orders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({
        MockitoExtension.class
})
public class OrdersServiceTest {
    @InjectMocks
    private OrdersService ordersService;
    @Mock
    private OrdersDao ordersDao;

    @Test
    void findOrdersByCustomer_WithValidData_ReturnsListOfOrdersDto() {
        Orders order1 = new Orders(1, LocalDateTime.of(2022, 8, 12, 5, 3),
                LocalDateTime.now(), "Received", 1);
        Orders order2 = new Orders(2, LocalDateTime.of(2023, 4, 12, 5, 3),
                LocalDateTime.now(), "Received", 1);

        when(ordersDao.findOrdersByCustomer(1)).thenReturn(List.of(order1, order2));

        List<OrdersDto> ordersDtoList = ordersService.findOrdersByCustomer(1);

        assertThat(ordersDtoList).isNotEmpty()
                .hasSize(2)
                .extracting("order_id", "order_date", "order_status", "customer_id")
                .containsExactly(
                        tuple(order1.getOrder_id(), order1.getOrder_date(), order1.getOrder_status(),
                                order1.getCustomer_id()),
                        tuple(order2.getOrder_id(), order2.getOrder_date(), order2.getOrder_status(),
                                order2.getCustomer_id())
                );

        verify(ordersDao, times(1)).findOrdersByCustomer(1);
    }

    @Test
    void findOrdersByItem_WithValidData_ReturnsListOfOrdersDto() {
        Orders order1 = new Orders(1, LocalDateTime.of(2022, 8, 12, 5, 3),
                LocalDateTime.now(), "Received", 1);
        Orders order2 = new Orders(2, LocalDateTime.of(2023, 4, 12, 5, 3),
                LocalDateTime.now(), "Received", 1);

        when(ordersDao.findOrdersByItems(1)).thenReturn(List.of(order1, order2));

        List<OrdersDto> ordersDtoList = ordersService.findOrdersByItems(1);

        assertThat(ordersDtoList).isNotEmpty()
                .hasSize(2)
                .extracting("order_id", "order_date", "order_status", "customer_id")
                .containsExactly(
                        tuple(order1.getOrder_id(), order1.getOrder_date(), order1.getOrder_status(),
                                order1.getCustomer_id()),
                        tuple(order2.getOrder_id(), order2.getOrder_date(), order2.getOrder_status(),
                                order2.getCustomer_id())
                );

        verify(ordersDao, times(1)).findOrdersByItems(1);
    }
}
