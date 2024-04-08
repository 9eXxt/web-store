package com.webstore.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "session_token")
@ToString(exclude = "customer")
@Entity
@Table(name = "user_session")
public class UserSession {
    @Id
    private String session_token;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String ip_address;
    private String device_info;
    private Timestamp expires_at;
}
