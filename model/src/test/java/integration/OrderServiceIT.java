package integration;

import dao.OrderDao;
import dto.OrderReadDto;
import integration.util.ConnectionTestUtil;
import integration.util.TestDataImporter;
import mapper.OrderReadMapper;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.OrderService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceIT {
    private OrderService orderService;
    private static final SessionFactory sessionFactory = ConnectionTestUtil.buildSessionFactory();

    @BeforeAll
    static void init() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @BeforeEach
    void setUp() {
        OrderDao orderDao = new OrderDao();
        OrderReadMapper orderReadMapper = new OrderReadMapper();
        orderService = new OrderService(orderDao, orderReadMapper, sessionFactory);

    }

    @Test
    void findOrdersByCustomer_WithValidData_ReturnsListOfOrdersDto() {
        List<OrderReadDto> orderDtoList = orderService.findOrdersByCustomer(1);

        assertThat(orderDtoList).isNotEmpty();
    }

    @Test
    void findOrdersByItem_WithValidData_ReturnsListOfOrdersDto() {
        List<OrderReadDto> ordersDtoList = orderService.findOrdersByItems(1);

        assertThat(ordersDtoList).isNotEmpty();
    }
}
