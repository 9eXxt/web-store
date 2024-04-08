package com.webstore.integration.model.service;

import com.webstore.model.dto.UserSessionCreateDto;
import com.webstore.model.dto.UserSessionReadDto;
import com.webstore.model.service.UserSessionService;
import com.webstore.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@RequiredArgsConstructor
public class UserSessionServiceIT extends IntegrationTestBase {
    private final UserSessionService userSessionService;
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

        assertThat(userSessionDto).isPresent();
    }
}
