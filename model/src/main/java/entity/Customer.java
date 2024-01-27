package entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
@Data
@AllArgsConstructor
@Builder
@ToString
public class Customer {
    private Integer customer_id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private String password;
    private String address;
}
