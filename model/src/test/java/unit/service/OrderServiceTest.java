package unit.service;

import dao.OrderDao;
import dto.OrderReadDto;
import entity.Customer;
import entity.Order;
import mapper.OrderReadMapper;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.OrderService;
import util.SessionUtil;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith({
        MockitoExtension.class
})
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderDao orderDao;
    @Mock
    private OrderReadMapper orderReadMapper;
    @Mock
    private SessionFactory sessionFactory;
    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder()
                .customer_id(1)
                .build();
        order1 = Order.builder()
                .order_id(1)
                .order_date(LocalDateTime.of(2022, 8, 12, 5, 3))
                .close_order_date(LocalDateTime.now())
                .order_status("Received")
                .customer(customer)
                .build();

        Customer customer1 = Customer.builder()
                .customer_id(2)
                .build();
        order2 = Order.builder()
                .order_id(2)
                .order_date(LocalDateTime.of(2023, 7, 12, 5, 4))
                .close_order_date(null)
                .order_status("Sent")
                .customer(customer1)
                .build();
    }

    @Test
    void findOrdersByCustomer_WithValidData_ReturnsListOfOrdersDto() {

        List<Order> orders = List.of(order1, order2);
        when(orderDao.findOrdersByCustomer(1)).thenReturn(orders);
        orders.forEach(order -> when(orderReadMapper.mapFrom(order)).thenReturn(new OrderReadDto(
                order.getOrder_id(),
                order.getOrder_date(),
                order.getOrder_status(),
                order.getCustomer().getCustomer_id()
        )));


        try (MockedStatic<SessionUtil> mockedStatic = Mockito.mockStatic(SessionUtil.class)) {
            List<OrderReadDto> orderDtoList = orderService.findOrdersByCustomer(1);

            assertThat(orderDtoList).isNotEmpty()
                    .hasSize(2)
                    .extracting("order_id", "order_date", "order_status", "customer_id")
                    .containsExactly(
                            tuple(order1.getOrder_id(), order1.getOrder_date(), order1.getOrder_status(),
                                    order1.getCustomer().getCustomer_id()),
                            tuple(order2.getOrder_id(), order2.getOrder_date(), order2.getOrder_status(),
                                    order2.getCustomer().getCustomer_id())
                    );

            verify(orderDao, times(1)).findOrdersByCustomer(1);
        }
    }

    @Test
    void findOrdersByItem_WithValidData_ReturnsListOfOrdersDto() {
        List<Order> orders = List.of(order1, order2);
        when(orderDao.findOrdersByItem(1)).thenReturn(orders);
        orders.forEach(order -> when(orderReadMapper.mapFrom(order)).thenReturn(new OrderReadDto(
                order.getOrder_id(),
                order.getOrder_date(),
                order.getOrder_status(),
                order.getCustomer().getCustomer_id()
        )));

        try (MockedStatic<SessionUtil> mockedStatic = Mockito.mockStatic(SessionUtil.class)) {
            List<OrderReadDto> ordersDtoList = orderService.findOrdersByItems(1);

            assertThat(ordersDtoList).isNotEmpty()
                    .hasSize(2)
                    .extracting("order_id", "order_date", "order_status", "customer_id")
                    .containsExactly(
                            tuple(order1.getOrder_id(), order1.getOrder_date(), order1.getOrder_status(),
                                    order1.getCustomer().getCustomer_id()),
                            tuple(order2.getOrder_id(), order2.getOrder_date(), order2.getOrder_status(),
                                    order2.getCustomer().getCustomer_id())
                    );

            verify(orderDao, times(1)).findOrdersByItem(1);
        }
    }
}
