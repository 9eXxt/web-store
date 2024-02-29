package servlets;

import dao.CustomerDao;
import dao.UserSessionDao;
import dto.UserSessionCreateDto;
import dto.CustomerReadDto;
import dto.UserSessionReadDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import mapper.CustomerMapper;
import mapper.CustomerReadMapper;
import mapper.UserSessionMapper;
import mapper.UserSessionReadMapper;
import org.hibernate.SessionFactory;
import service.CustomerService;
import service.UserSessionService;
import util.ConnectionUtil;
import util.JspHelper;

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
        SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
        customerService = new CustomerService(
                new CustomerDao(),
                new CustomerMapper(),
                new CustomerReadMapper(),
                sessionFactory
        );
        userSessionService = new UserSessionService(
                new UserSessionDao(),
                new UserSessionMapper(),
                new UserSessionReadMapper(),
                sessionFactory
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
    private void loginSuccess(CustomerReadDto customerReadDto, HttpServletRequest req, HttpServletResponse resp) {
        String IP = req.getRemoteAddr();
        String deviceInfo = req.getHeader("User-Agent");

        Optional<UserSessionReadDto> existingToken = userSessionService
                .findToken(customerReadDto.getCustomer_id());

        String sessionToken;
        if (existingToken.isPresent()) {
            sessionToken = existingToken.get().session_token();
        } else {
            sessionToken = UUID.randomUUID().toString();
            userSessionService.create(new UserSessionCreateDto(
                    sessionToken,
                    customerReadDto.getCustomer_id(),
                    IP,
                    deviceInfo,
                    Timestamp.valueOf(LocalDateTime.now().plusDays(30))));
        }

        Cookie sessionCookie = new Cookie("sessionToken", sessionToken);
        sessionCookie.setMaxAge(60 * 60 * 24 * 30);
        resp.addCookie(sessionCookie);

        req.getSession().setAttribute("customer", customerReadDto);
        resp.sendRedirect("/shop/orders?customerId=" + customerReadDto.getCustomer_id());
    }

    @SneakyThrows
    private void loginWrong(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/shop/login?error&email=" + req.getParameter("email"));
    }
}
