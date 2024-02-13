package mapper;

import dto.CreateUserSessionDto;
import entity.Customer;
import entity.User_Session;

public class CreateUserSessionMapper implements Mapper<CreateUserSessionDto, User_Session> {
    @Override
    public User_Session mapFrom(CreateUserSessionDto object) {
        Customer customer = Customer.builder()
                .customer_id(object.getCustomer_id())
                .build();
        return User_Session.builder()
                .session_token(object.getSession_Token())
                .customer(customer)
                .ip_address(object.getIp_address())
                .device_info(object.getDevice_info())
                .expires_at(object.getExpires_at())
                .build();
    }
}
