package dao;

import com.querydsl.jpa.impl.JPAQuery;
import entity.Items;
import util.ConnectionUtil;
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
