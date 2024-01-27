package access;

import dto.CustomerDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

public class LoggedInAccessRule implements AccessRule{
    private final Set<String> loggedInPaths = Set.of("/shop/items", "/shop/images", "/shop/locale", "/shop/logout");
    @Override
    public boolean isAllowed(HttpServletRequest request, CustomerDto customerDto) {
        String URI = request.getRequestURI();

        if (customerDto == null) {
            return false;
        }

        if (URI.startsWith("/shop/orders")) {
            String customerIdParam = request.getParameter("customerId");
            return customerIdParam != null && customerIdParam.equals(String.valueOf(customerDto.getCustomer_id()));
        }

        return loggedInPaths.stream().anyMatch(URI::startsWith);
    }
}
