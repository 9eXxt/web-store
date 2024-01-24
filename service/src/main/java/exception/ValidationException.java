package exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import validator.Error;
import java.util.List;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException {
    private final List<Error> errors;
}
