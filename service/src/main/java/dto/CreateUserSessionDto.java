package dto;


import lombok.Value;
import java.sql.Timestamp;

@Value
public class CreateUserSessionDto {
    String session_Token;
    Integer customer_id;
    String ip_address;
    String device_info;
    Timestamp expires_at;
}
