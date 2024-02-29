package access;

import dto.CustomerReadDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AccessRule {
    boolean isAllowed(HttpServletRequest request, CustomerReadDto customerReadDto);
}
