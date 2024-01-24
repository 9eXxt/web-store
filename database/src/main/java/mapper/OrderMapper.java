package mapper;

import entity.Orders;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.time.LocalDateTime;


public class OrderMapper implements EntityMapper<Orders>{
    @SneakyThrows
    @Override
    public Orders buildEntity(ResultSet resultSet) {
        return Orders.builder()
                .order_id(resultSet.getInt("order_id"))
                .order_date(resultSet.getObject("order_date", LocalDateTime.class))
                .close_order_date(resultSet.getObject("close_order_date", LocalDateTime.class))
                .order_status(resultSet.getString("order_status"))
                .customer_id(resultSet.getInt("customer_id"))
                .build();
    }
}
