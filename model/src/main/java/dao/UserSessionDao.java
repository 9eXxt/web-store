package dao;

import entity.User_Session;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@AllArgsConstructor
public class UserSessionDao {
    private static final String CREATE_SESSION = """
            INSERT INTO user_sessions(session_token, customer_id, ip_address, device_info, expires_at)
            VALUES (?,?,?,?,?)""";
    private static final String FIND_TOKEN = """
            SELECT *
            FROM user_sessions
            WHERE customer_id = ?
            AND  ip_address = ?
            AND device_info = ?""";

    @SneakyThrows
    public Optional<User_Session> findToken(Integer customer_id, String ip_address, String device_info) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_TOKEN)) {
            preparedStatement.setInt(1, customer_id);
            preparedStatement.setString(2, ip_address);
            preparedStatement.setString(3, device_info);

            ResultSet resultSet = preparedStatement.executeQuery();
            User_Session userSession = null;
            if (resultSet.next()) {
                userSession = buildEntity(resultSet);
            }
            return Optional.ofNullable(userSession);
        }
    }

    @SneakyThrows
    public void createSession(User_Session userSession) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_SESSION)) {
            preparedStatement.setObject(1, userSession.getSession_token());
            preparedStatement.setObject(2, userSession.getCustomer_id());
            preparedStatement.setObject(3, userSession.getIp_address());
            preparedStatement.setObject(4, userSession.getDevice_info());
            preparedStatement.setObject(5, userSession.getExpires_at());

            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    private User_Session buildEntity(ResultSet resultSet) {
        return User_Session.builder()
                .session_token(resultSet.getString("session_token"))
                .customer_id(resultSet.getInt("customer_id"))
                .ip_address(resultSet.getString("ip_address"))
                .device_info(resultSet.getString("device_info"))
                .expires_at(resultSet.getTimestamp("expires_at"))
                .build();
    }
}
