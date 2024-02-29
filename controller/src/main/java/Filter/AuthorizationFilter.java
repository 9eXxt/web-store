package Filter;

import access.AccessRule;
import access.AdminAccessRule;
import access.LoggedInAccessRule;
import access.PublicAccessRule;
import dao.CustomerDao;
import dto.CustomerReadDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.CustomerMapper;
import mapper.CustomerReadMapper;
import org.hibernate.SessionFactory;
import service.CustomerService;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private final CustomerService customerService;
    private final List<AccessRule> accessRules;

    public AuthorizationFilter() {
        SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
        customerService = new CustomerService(
                new CustomerDao(),
                new CustomerMapper(),
                new CustomerReadMapper(),
                sessionFactory
        );

        accessRules = new ArrayList<>();
        accessRules.add(new AdminAccessRule());
        accessRules.add(new LoggedInAccessRule());
        accessRules.add(new PublicAccessRule());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        setLanguage(httpServletRequest);
        checkSessionToken(httpServletRequest);

        CustomerReadDto customerReadDto = (CustomerReadDto) httpServletRequest.getSession().getAttribute("customer");

        for (AccessRule rule : accessRules) {
            if (rule.isAllowed(httpServletRequest, customerReadDto)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        httpServletResponse.sendRedirect("/shop/items");
    }

    private void setLanguage(HttpServletRequest request) {
        if (request.getSession().getAttribute("lang") == null) {
            Locale preferredLocale = request.getLocale();
            String cookieInfo = getCookieValue(request, "userLang");
            String language = cookieInfo != null ? cookieInfo :
                    (isSupportedLocale(preferredLocale) ? preferredLocale.toString() : "en_GB");
            request.getSession().setAttribute("lang", language);
        }
    }

    private void checkSessionToken(HttpServletRequest request) {
        if (request.getSession().getAttribute("tokenChecked") != null) return;

        String cookieInfo = getCookieValue(request, "sessionToken");
        if (cookieInfo == null) {
            return;
        }
        Optional<CustomerReadDto> userSession = customerService.findByToken(cookieInfo);
        userSession.ifPresent(customerDto -> request.getSession().setAttribute("customer", customerDto));

        request.getSession().setAttribute("tokenChecked", true);
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private boolean isSupportedLocale(Locale preferredLocale) {
        try {
            ResourceBundle.getBundle("translations", preferredLocale);
            return true;
        } catch (MissingResourceException e) {
            return false;
        }
    }
}
