package access;

import dto.CustomerReadDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

public class LoggedInAccessRule implements AccessRule{
    private final Set<String> loggedInPaths = Set.of("/shop/items", "/shop/images", "/shop/locale", "/shop/logout");
    @Override
    public boolean isAllowed(HttpServletRequest request, CustomerReadDto customerReadDto) {
        String URI = request.getRequestURI();

        if (customerReadDto == null) {
            return false;
        }

        if (URI.startsWith("/shop/orders")) {
            String customerIdParam = request.getParameter("customerId");
            return customerIdParam != null && customerIdParam.equals(String.valueOf(customerReadDto.getCustomer_id()));
        }

        return loggedInPaths.stream().anyMatch(URI::startsWith);
    }
}
