package mapper;

import dto.CreateUserSessionDto;
import entity.User_Session;

public class CreateUserSessionMapper implements Mapper<CreateUserSessionDto, User_Session> {
    @Override
    public User_Session mapFrom(CreateUserSessionDto object) {
        return new User_Session(
                object.getSession_Token(),
                object.getCustomer_id(),
                object.getIp_address(),
                object.getDevice_info(),
                object.getExpires_at()
        );
    }
}
