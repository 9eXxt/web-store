package dao;


import entity.Orders;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import mapper.OrderMapper;
import util.ConnectionManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class OrdersDao {
    private final OrderMapper orderMapper;
    private static final String FIND_ORDERS_BY_CUSTOMERS = """
            SELECT *
            FROM orders
            WHERE customer_id = ?""";
    private static final String FIND_ORDERS_BY_ITEMS = """
            SELECT order_id, order_date, close_order_date, order_status, orders.customer_id
            FROM order_items
            JOIN items ON items_id = item_id
            JOIN orders ON order_items_id = order_id
            JOIN customer ON orders.customer_id = customer.customer_id
            WHERE items_id = ?;""";

    @SneakyThrows
    public List<Orders> findOrdersByCustomer(Integer customer_id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDERS_BY_CUSTOMERS)) {
            preparedStatement.setObject(1, customer_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Orders> customerList = new ArrayList<>();
            while (resultSet.next()) {
                customerList.add(orderMapper.buildEntity(resultSet));
            }
            return customerList;
        }
    }

    @SneakyThrows
    public List<Orders> findOrdersByItems(Integer items_id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDERS_BY_ITEMS)) {
            preparedStatement.setObject(1, items_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Orders> ordersList = new ArrayList<>();
            while (resultSet.next()) {
                ordersList.add(orderMapper.buildEntity(resultSet));
            }
            return ordersList;
        }
    }
}
