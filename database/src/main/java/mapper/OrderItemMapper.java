package mapper;

import entity.OrderItems;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class OrderItemMapper implements EntityMapper<OrderItems> {
    @SneakyThrows
    @Override
    public OrderItems buildEntity(ResultSet resultSet) {
        return OrderItems.builder()
                .order_items_id(resultSet.getInt("order_items_id"))
                .order_number(resultSet.getInt("order_number"))
                .name_item(resultSet.getString("name_item"))
                .count(resultSet.getInt("count"))
                .build();
    }
}
