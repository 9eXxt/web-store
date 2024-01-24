package validator;

import lombok.Value;

@Value
public class Error {
    String code;
    String message;
}
