package mapper;

import dto.UserSessionCreateDto;
import entity.Customer;
import entity.UserSession;

public class UserSessionMapper implements Mapper<UserSessionCreateDto, UserSession> {
    @Override
    public UserSession mapFrom(UserSessionCreateDto object) {
        Customer customer = Customer.builder()
                .customer_id(object.getCustomer_id())
                .build();
        return UserSession.builder()
                .session_token(object.getSession_token())
                .customer(customer)
                .ip_address(object.getIp_address())
                .device_info(object.getDevice_info())
                .expires_at(object.getExpires_at())
                .build();
    }
}
