package service;

import dao.UserSessionDao;
import dto.CreateUserSessionDto;
import dto.UserSessionDto;
import entity.User_Session;
import mapper.CreateUserSessionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    private UserSessionDao userSessionDao;
    @Mock
    private CreateUserSessionMapper createUserSessionMapper;

    @Test
    void create_WithValidData() {
        CreateUserSessionDto createUserSessionDto = new CreateUserSessionDto(
                "23dsfd", 1, "192.168.1.1", "Lenovo",
                Timestamp.valueOf(LocalDateTime.now()));
        User_Session userSession = new User_Session(
                "23dsfd", 1, "192.168.1.1", "Lenovo",
                Timestamp.valueOf(LocalDateTime.now()));
        when(createUserSessionMapper.mapFrom(createUserSessionDto)).thenReturn(userSession);

        userSessionService.create(createUserSessionDto);

        verify(createUserSessionMapper).mapFrom(createUserSessionDto);
        verify(userSessionDao).createSession(userSession);
    }

    @Test
    void findToken_WithValidData_ReturnsOptionalOfUserSessionDto() {
        Integer customer_id = 1;
        String ip_address = "192.168.1.1";
        String device_info = "Lenovo";
        User_Session userSession = new User_Session("23dsfd", customer_id, ip_address, device_info,
                Timestamp.valueOf(LocalDateTime.now()));

        when(userSessionDao.findToken(customer_id, ip_address, device_info)).thenReturn(Optional.of(userSession));

        Optional<UserSessionDto> userSessionDto = userSessionService.findToken(customer_id, ip_address, device_info);

        assertThat(userSessionDto).isPresent()
                .hasValueSatisfying(userSessionDto1 -> assertThat(userSessionDto1.getSession_token())
                        .isEqualTo(userSession.getSession_token()));

        verify(userSessionDao).findToken(customer_id, ip_address, device_info);
    }
}
