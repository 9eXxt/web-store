package integration;

import dao.UserSessionDao;
import dto.UserSessionCreateDto;
import dto.UserSessionReadDto;
import entity.UserSession;
import integration.util.ConnectionTestUtil;
import integration.util.TestDataImporter;
import mapper.UserSessionMapper;
import mapper.UserSessionReadMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserSessionService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class UserSessionServiceIT {
    private UserSessionService userSessionService;
    private static final SessionFactory sessionFactory = ConnectionTestUtil.buildSessionFactory();
    private UserSession userSession;

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
        UserSessionDao userSessionDao = new UserSessionDao();
        UserSessionMapper userSessionMapper = new UserSessionMapper();
        UserSessionReadMapper userSessionReadMapper = new UserSessionReadMapper();
        userSessionService = new UserSessionService(userSessionDao,
                userSessionMapper, userSessionReadMapper, sessionFactory);

        Session session = sessionFactory.openSession();
        userSession = session.get(UserSession.class, "token123");
        session.close();
    }

    @Test
    void create_WithValidData() {
        UserSessionCreateDto userSessionCreateDto = new UserSessionCreateDto(
                "23dsfd", 3, "192.168.1.1", "Lenovo",
                Timestamp.valueOf(LocalDateTime.now()));

        userSessionService.create(userSessionCreateDto);

        Optional<UserSessionReadDto> findCreatedValue = userSessionService.findToken(3);
        assertThat(findCreatedValue).hasValueSatisfying(userSessionReadDto ->
                assertThat(userSessionReadDto.session_token()).isEqualTo(userSessionCreateDto.getSession_token()));
    }

    @Test
    void findToken_WithValidData_ReturnsOptionalOfUserSessionDto() {
        Optional<UserSessionReadDto> userSessionDto = userSessionService.findToken(1);

        assertThat(userSessionDto).isPresent().hasValueSatisfying(userSessionReadDto ->
                assertThat(userSessionReadDto.session_token()).isEqualTo(userSession.getSession_token()));
    }
}
