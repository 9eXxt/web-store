package validator;

import dto.CreateCustomerDto;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

public class CreateCustomerValidator implements Validator<CreateCustomerDto> {
    @Override
    public ValidationResult isValid(CreateCustomerDto object) {
        ValidationResult validationResult = new ValidationResult();
        if (!object.getFirst_name().matches("[A-Za-z]+")) {
            validationResult.add(new Error("invalid.first_name", "First name isn't correct"));
        }
        if (!object.getLast_name().matches("[A-Za-z]+")) {
            validationResult.add(new Error("invalid.last_name", "Last name isn't correct"));
        }
        if (!object.getPhone_number().matches("[0-9]+")) {
            validationResult.add(new Error("invalid.phone_number", "Phone number isn't correct"));
        }
        if (!object.getPhone_number().matches("[0-9]+")) {
            validationResult.add(new Error("invalid.phone_number", "Phone number isn't correct"));
        }
        return validationResult;
    }
}
