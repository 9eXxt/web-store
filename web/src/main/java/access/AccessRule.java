package access;

import dto.CustomerDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AccessRule {
    boolean isAllowed(HttpServletRequest request, CustomerDto customerDto);
}
