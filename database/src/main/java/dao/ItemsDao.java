package dao;

import entity.Items;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import mapper.ItemMapper;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ItemsDao {
    private final ItemMapper itemMapper;
    private static final String FIND_ALL = """
            SELECT *
            FROM items""";

    @SneakyThrows
    public List<Items> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Items> itemsList = new ArrayList<>();
            while (resultSet.next()) {
                itemsList.add(itemMapper.buildEntity(resultSet));
            }
            return itemsList;
        }
    }
}
