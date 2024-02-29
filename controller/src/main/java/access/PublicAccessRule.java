package access;

import dto.CustomerReadDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

public class PublicAccessRule implements AccessRule{
    private final Set<String> publicPaths = Set.of("/shop/login", "/shop/registration", "/shop/items", "/shop/images"
            ,"/shop/locale");
    @Override
    public boolean isAllowed(HttpServletRequest request, CustomerReadDto customerReadDto) {
        String URI = request.getRequestURI();
        return customerReadDto == null && publicPaths.stream().anyMatch(URI::startsWith);
    }
}
