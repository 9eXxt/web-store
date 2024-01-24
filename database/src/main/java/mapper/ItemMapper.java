package mapper;

import entity.Items;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public class ItemMapper implements EntityMapper<Items> {
    @SneakyThrows
    @Override
    public Items buildEntity(ResultSet resultSet) {
        return Items.builder()
                .items_id(resultSet.getInt("items_id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getBigDecimal("price"))
                .quantity_left(resultSet.getInt("quantity_left"))
                .build();
    }
}
