package com.webstore.unit.service;


import com.webstore.model.dto.UserSessionCreateDto;
import com.webstore.model.dto.UserSessionReadDto;
import com.webstore.model.entity.Customer;
import com.webstore.model.entity.PersonalInfo;
import com.webstore.model.entity.UserSession;
import com.webstore.model.mapper.UserSessionMapper;
import com.webstore.model.mapper.UserSessionReadMapper;
import com.webstore.model.repository.UserSessionRepository;
import com.webstore.model.service.UserSessionService;
import com.webstore.model.util.SessionUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;


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
    private UserSessionRepository userSessionRepository;
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

        try(MockedStatic<SessionUtil> mockedStatic = mockStatic(SessionUtil.class)) {

            userSessionService.create(userSessionCreateDto);

            verify(userSessionMapper).mapFrom(userSessionCreateDto);
            verify(userSessionRepository, times(1)).save(userSession);
        }
    }

    @Test
    void findToken_WithValidData_ReturnsOptionalOfUserSessionDto() {
        when(userSessionRepository.findToken(1)).thenReturn(Optional.of(userSession));
        when(userSessionReadMapper.mapFrom(userSession)).thenReturn(new UserSessionReadDto(
                userSession.getSession_token()
        ));

        try(MockedStatic<SessionUtil> mockedStatic = mockStatic(SessionUtil.class)) {
            Optional<UserSessionReadDto> userSessionDto = userSessionService.findToken(1);

            assertThat(userSessionDto).isPresent()
                    .hasValueSatisfying(userSessionReadDto1 -> assertThat(userSessionReadDto1.session_token())
                            .isEqualTo(userSession.getSession_token()));

            verify(userSessionRepository, times(1)).findToken(1);
        }
    }
}
