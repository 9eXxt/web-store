package mapper;

import java.sql.ResultSet;

public interface EntityMapper<T> {
    T buildEntity(ResultSet resultSet);
}
