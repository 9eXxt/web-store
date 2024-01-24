package access;

import dto.CustomerDto;
import jakarta.servlet.http.HttpServletRequest;

public class AdminAccessRule implements AccessRule{
    @Override
    public boolean isAllowed(HttpServletRequest request, CustomerDto customerDto) {
        return customerDto != null && customerDto.getEmail().equals("admin");
    }
}
