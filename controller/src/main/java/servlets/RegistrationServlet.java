package servlets;

import dao.CustomerDao;
import dto.CreateCustomerDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.CreateCustomerMapper;
import mapper.CustomerMapper;
import service.CustomerService;
import util.JspHelper;
import validator.CreateCustomerValidator;

import java.io.IOException;

@WebServlet(value = "/registration", name = "RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService(
                new CustomerDao(),
                new CreateCustomerValidator(),
                new CreateCustomerMapper()
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        customerService.create(new CreateCustomerDto(req.getParameter("first_name"),
                req.getParameter("last_name"),
                req.getParameter("phone_number"),
                req.getParameter("email"),
                req.getParameter("password"),
                req.getParameter("address")));
        resp.sendRedirect("/login");
        req.getParameter("first_name");
    }
}
