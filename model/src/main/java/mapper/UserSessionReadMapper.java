package mapper;

import dto.UserSessionReadDto;
import entity.UserSession;

public class UserSessionReadMapper implements Mapper<UserSession, UserSessionReadDto>{
    @Override
    public UserSessionReadDto mapFrom(UserSession object) {
        return new UserSessionReadDto(object.getSession_token());
    }
}
