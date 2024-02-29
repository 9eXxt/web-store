package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class CustomerCreateDto {
    @Pattern(regexp = "[A-Za-z]+", message = "{invalid.first_name}")
    String first_name;
    @Pattern(regexp = "[A-Za-z]+", message = "{invalid.last_name}")
    String last_name;
    @NotNull
    @Pattern(regexp = "^\\+\\d{10,15}$", message = "{invalid.phone_number}")
    String phone_number;
    @NotNull
    @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
            message = "{invalid.email}")
    String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$",
            message = "{invalid.password}")
    String password;
    String address;
}

// !!!!!!!!!!!!!!!!!!!! records are not working with jsp

//public record CustomerCreateDto(@Pattern(regexp = "[A-Za-z]+", message = "{invalid.first_name}")
//                                String first_name,
//                                @Pattern(regexp = "[A-Za-z]+", message = "{invalid.last_name}")
//                                String last_name,
//                                @NotNull
//                                @Pattern(regexp = "^\\+\\d{10,15}$", message = "{invalid.phone_number}")
//                                String phone_number,
//                                @NotNull
//                                @Email(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",
//                                        message = "{invalid.email}")
//                                String email,
//                                @NotNull
//                                @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[#?!@$%^&*-]).{8,}$",
//                                        message = "{invalid.password}")
//                                String password,
//                                String address) {
//}
