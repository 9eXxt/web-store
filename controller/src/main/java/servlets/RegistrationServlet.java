package servlets;

import dao.CustomerDao;
import dto.CustomerCreateDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.CustomerMapper;
import mapper.CustomerReadMapper;
import org.hibernate.SessionFactory;
import service.CustomerService;
import util.ConnectionUtil;
import util.JspHelper;

import java.io.IOException;

@WebServlet(value = "/registration", name = "RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
        customerService = new CustomerService(
                new CustomerDao(),
                new CustomerMapper(),
                new CustomerReadMapper(),
                sessionFactory
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        customerService.createUser(new CustomerCreateDto(req.getParameter("first_name"),
                req.getParameter("last_name"),
                req.getParameter("phone_number"),
                req.getParameter("email"),
                req.getParameter("password"),
                req.getParameter("address")));
        resp.sendRedirect("/shop/login");
        req.getParameter("first_name");
    }
}
