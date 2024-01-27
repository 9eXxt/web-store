package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Builder
@ToString
public class User_Session {
    private String session_token;
    private Integer customer_id;
    private String ip_address;
    private String device_info;
    private Timestamp expires_at;
}
