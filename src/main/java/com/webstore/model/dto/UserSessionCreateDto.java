package com.webstore.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.sql.Timestamp;
@Value
public class UserSessionCreateDto {
    @NotNull
    String session_token;
    Integer customer_id;
    String ip_address;
    String device_info;
    Timestamp expires_at;
}

//public record UserSessionCreateDto(@NotNull
//                                  String session_token,
//                                   Integer customer_id,
//                                   String ip_address,
//                                   String device_info,
//                                   Timestamp expires_at) {
//}
