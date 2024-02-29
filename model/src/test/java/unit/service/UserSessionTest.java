package unit.service;

import dao.UserSessionDao;
import dto.UserSessionCreateDto;
import dto.UserSessionReadDto;
import entity.Customer;
import entity.PersonalInfo;
import entity.UserSession;
import mapper.UserSessionMapper;
import mapper.UserSessionReadMapper;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import service.UserSessionService;
import util.SessionUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({
        MockitoExtension.class
})
public class UserSessionTest {
    @InjectMocks
    private UserSessionService userSessionService;
    @Mock
    private UserSessionDao userSessionDao;
    @Mock
    private UserSessionMapper userSessionMapper;
    @Mock
    private UserSessionReadMapper userSessionReadMapper;
    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;

    private UserSession userSession;
    @BeforeEach
    void setUp() {
        PersonalInfo personalInfo = PersonalInfo.builder()
                .first_name("Bob")
                .last_name("Biden")
                .build();
        Customer customer = Customer.builder()
                .customer_id(1)
                .personalInfo(personalInfo)
                .email("ivan.ivanov@example.com")
                .phone_number("123-424-2222")
                .password("password123")
                .build();

        userSession = new UserSession(
                "23dsfd", customer, "192.168.1.1", "Lenovo",
                Timestamp.valueOf(LocalDateTime.now()));
    }
    @Test
    void create_WithValidData() {
        UserSessionCreateDto userSessionCreateDto = new UserSessionCreateDto(
                "23dsfd", 1, "192.168.1.1", "Lenovo",
                Timestamp.valueOf(LocalDateTime.now()));

        when(userSessionMapper.mapFrom(userSessionCreateDto)).thenReturn(userSession);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.getTransaction()).thenReturn(transaction);

        try(MockedStatic<SessionUtil> mockedStatic = Mockito.mockStatic(SessionUtil.class)) {
            mockedStatic.when(() -> SessionUtil.openSession(sessionFactory)).thenReturn(session);

            userSessionService.create(userSessionCreateDto);

            verify(userSessionMapper).mapFrom(userSessionCreateDto);
            verify(userSessionDao, times(1)).save(userSession);
        }
    }

    @Test
    void findToken_WithValidData_ReturnsOptionalOfUserSessionDto() {
        when(userSessionDao.findToken(1)).thenReturn(Optional.of(userSession));
        when(userSessionReadMapper.mapFrom(userSession)).thenReturn(new UserSessionReadDto(
                userSession.getSession_token()
        ));

        try(MockedStatic<SessionUtil> mockedStatic = Mockito.mockStatic(SessionUtil.class)) {
            Optional<UserSessionReadDto> userSessionDto = userSessionService.findToken(1);

            Assertions.assertThat(userSessionDto).isPresent()
                    .hasValueSatisfying(userSessionReadDto1 -> assertThat(userSessionReadDto1.session_token())
                            .isEqualTo(userSession.getSession_token()));

            verify(userSessionDao, times(1)).findToken(1);
        }
    }
}
