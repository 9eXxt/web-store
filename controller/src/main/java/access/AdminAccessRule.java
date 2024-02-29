package access;

import dto.CustomerReadDto;
import jakarta.servlet.http.HttpServletRequest;

public class AdminAccessRule implements AccessRule{
    @Override
    public boolean isAllowed(HttpServletRequest request, CustomerReadDto customerReadDto) {
        return customerReadDto != null && customerReadDto.getEmail().equals("admin");
    }
}
