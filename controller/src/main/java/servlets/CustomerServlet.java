package servlets;

import dao.CustomerDao;
import dto.CustomerReadDto;
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
import java.util.stream.Collectors;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
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
        req.setAttribute("customers", customerService.findAll());
        req.getSession().setAttribute("customers", customerService.findAll().stream()
                .collect(Collectors.toMap(CustomerReadDto::getCustomer_id, CustomerReadDto::getPersonalInfo)));
        req.getRequestDispatcher(JspHelper.getPath("customer"))
                .forward(req, resp);
    }
}
