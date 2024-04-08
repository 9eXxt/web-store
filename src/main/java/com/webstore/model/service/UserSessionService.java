package com.webstore.model.service;
import com.webstore.model.dto.UserSessionCreateDto;
import com.webstore.model.dto.UserSessionReadDto;
import com.webstore.model.entity.UserSession;
import com.webstore.model.mapper.UserSessionMapper;
import com.webstore.model.mapper.UserSessionReadMapper;
import com.webstore.model.repository.UserSessionRepository;

import com.webstore.model.util.SessionUtil;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserSessionService {
    private final UserSessionRepository userSessionRepository;
    private final UserSessionMapper userSessionMapper;
    private final UserSessionReadMapper userSessionReadMapper;
    @Transactional
    public void create(UserSessionCreateDto userSessionDto) {
            UserSession userSession = userSessionMapper.mapFrom(userSessionDto);
            userSessionRepository.save(userSession);
    }

    public Optional<UserSessionReadDto> findToken(Integer customer_id) {
            return userSessionRepository.findToken(customer_id)
                    .map(userSessionReadMapper::mapFrom);
    }
}
