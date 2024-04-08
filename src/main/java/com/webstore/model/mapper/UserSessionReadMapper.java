package com.webstore.model.mapper;


import com.webstore.model.dto.UserSessionReadDto;
import com.webstore.model.entity.UserSession;
import org.springframework.stereotype.Component;

@Component
public class UserSessionReadMapper implements Mapper<UserSession, UserSessionReadDto>{
    @Override
    public UserSessionReadDto mapFrom(UserSession object) {
        return new UserSessionReadDto(object.getSession_token());
    }
}
