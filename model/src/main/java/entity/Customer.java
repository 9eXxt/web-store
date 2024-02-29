package entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "order")
@EqualsAndHashCode(of = {"phone_number", "email"})
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "phone_number"),
        @UniqueConstraint(columnNames = "email")
})
public class Customer implements Manageable<Order> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customer_id;
    @Embedded
    private PersonalInfo personalInfo;
    private String phone_number;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Builder.Default
    @OneToMany(mappedBy = "customer", cascade = CascadeType.PERSIST)
    private List<Order> order = new ArrayList<>();

    @Override
    public void add(Order entity) {
        order.add(entity);
        entity.setCustomer(this);
    }
}
