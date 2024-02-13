package servlets;

import dao.CustomerDao;
import dao.UserSessionDao;
import dto.CreateUserSessionDto;
import dto.CustomerDto;
import dto.UserSessionDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import mapper.CreateCustomerMapper;
import mapper.CreateUserSessionMapper;
import service.CustomerService;
import service.UserSessionService;
import util.JspHelper;
import validator.CreateCustomerValidator;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private CustomerService customerService;
    private UserSessionService userSessionService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService(
                new CustomerDao(),
                new CreateCustomerValidator(),
                new CreateCustomerMapper()
        );
        userSessionService = new UserSessionService(
                new UserSessionDao(),
                new CreateUserSessionMapper()
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        customerService.login(req.getParameter("email"), req.getParameter("password"))
                .ifPresentOrElse(
                        customerDto -> loginSuccess(customerDto, req, resp),
                        () -> loginWrong(req, resp)
                );
    }

    @SneakyThrows
    private void loginSuccess(CustomerDto customerDto, HttpServletRequest req, HttpServletResponse resp) {
        String IP = req.getRemoteAddr();
        String deviceInfo = req.getHeader("User-Agent");

        Optional<UserSessionDto> existingToken = userSessionService
                .findToken(customerDto.getCustomer_id());

        String sessionToken;
        if (existingToken.isPresent()) {
            sessionToken = existingToken.get().getSession_token();
        } else {
            sessionToken = UUID.randomUUID().toString();
            userSessionService.create(new CreateUserSessionDto(
                    sessionToken,
                    customerDto.getCustomer_id(),
                    IP,
                    deviceInfo,
                    Timestamp.valueOf(LocalDateTime.now().plusDays(30))));
        }

        Cookie sessionCookie = new Cookie("sessionToken", sessionToken);
        sessionCookie.setMaxAge(60 * 60 * 24 * 30);
        resp.addCookie(sessionCookie);

        req.getSession().setAttribute("customer", customerDto);
        resp.sendRedirect("/shop/orders?customerId=" + customerDto.getCustomer_id());
    }

    @SneakyThrows
    private void loginWrong(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/shop/login?error&email=" + req.getParameter("email"));
    }
}
