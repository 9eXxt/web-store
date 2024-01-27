package servlets;

import dao.CustomerDao;
import dao.ItemsDao;
import dao.OrdersDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.CreateCustomerMapper;
import mapper.CustomerMapper;
import mapper.ItemMapper;
import mapper.OrderMapper;
import service.CustomerService;
import service.ItemsService;
import service.OrdersService;
import util.JspHelper;
import validator.CreateCustomerValidator;

import java.io.IOException;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private OrdersService ordersService;
    private CustomerService customerService;
    private ItemsService itemsService;

    @Override
    public void init() throws ServletException {
        ordersService = new OrdersService(
                new OrdersDao(new OrderMapper())
        );
        customerService = new CustomerService(
                new CustomerDao(new CustomerMapper()),
                new CreateCustomerValidator(),
                new CreateCustomerMapper()
        );
        itemsService = new ItemsService(
                new ItemsDao(new ItemMapper())
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("customerId") != null) {
            Integer customer_id = Integer.valueOf(req.getParameter("customerId"));
            req.setAttribute("customerOrders", ordersService.findOrdersByCustomer(customer_id));
            req.setAttribute("customerInfo", customerService.findById(customer_id).get());
            req.getRequestDispatcher(JspHelper.getPath("ordersByCustomer")).forward(req, resp);
        } else if (req.getParameter("itemsId") != null) {
            Integer item_id = Integer.valueOf(req.getParameter("itemsId"));
            req.setAttribute("itemOrders", ordersService.findOrdersByItems(item_id));
            req.setAttribute("itemInfo", itemsService.findAll());
            req.setAttribute("customerInfo", customerService.findAll());
            req.getRequestDispatcher(JspHelper.getPath("ordersByItem")).forward(req, resp);
        }

    }
}
