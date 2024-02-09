package dao;

import com.querydsl.jpa.impl.JPAQuery;
import entity.Items;

import entity.QItems;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import mapper.ItemMapper;
import org.hibernate.annotations.processing.HQL;
import util.ConnectionManager;
import util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static entity.QItems.*;

public class ItemsDao {
    public List<Items> findAll() {
        var connection = ConnectionUtil.getSessionFactory();
        try(var session = connection.openSession()) {
            return new JPAQuery<Items>(session)
                    .select(items)
                    .from(items)
                    .fetch();
        }
    }
}
