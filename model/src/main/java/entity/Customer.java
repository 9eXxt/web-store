package entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"phone_number", "email"})
@ToString(exclude = "userSession")
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"phone_number","email"})
})
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customer_id;
    @Embedded
    private PersonalInfo personalInfo;
    @NotNull
    private String phone_number;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "customer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false)
    private User_Session userSession;
}
