package servlets;

import dao.CustomerDao;
import dto.CustomerDto;
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
import java.util.stream.Collectors;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService(
                new CustomerDao(new CustomerMapper()),
                new CreateCustomerValidator(),
                new CreateCustomerMapper()
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("customers", customerService.findAll());
        req.getSession().setAttribute("customers", customerService.findAll().stream()
                .collect(Collectors.toMap(CustomerDto::getCustomer_id, CustomerDto::getName)));
        req.getRequestDispatcher(JspHelper.getPath("customer"))
                .forward(req, resp);
    }
}
