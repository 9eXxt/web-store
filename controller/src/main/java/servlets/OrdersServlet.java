package servlets;

import dao.CustomerDao;
import dao.ItemDao;
import dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.CustomerMapper;
import mapper.CustomerReadMapper;
import mapper.ItemReadMapper;
import mapper.OrderReadMapper;
import org.hibernate.SessionFactory;
import service.CustomerService;
import service.ItemService;
import service.OrderService;
import util.ConnectionUtil;
import util.JspHelper;

import java.io.IOException;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    private OrderService orderService;
    private CustomerService customerService;
    private ItemService itemService;

    @Override
    public void init() throws ServletException {
        SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
        orderService = new OrderService(
                new OrderDao(),
                new OrderReadMapper(),
                sessionFactory
        );
        customerService = new CustomerService(
                new CustomerDao(),
                new CustomerMapper(),
                new CustomerReadMapper(),
                sessionFactory
        );
        itemService = new ItemService(
                new ItemDao(),
                new ItemReadMapper(),
                sessionFactory
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("customerId") != null) {
            Integer customer_id = Integer.valueOf(req.getParameter("customerId"));
            req.setAttribute("customerOrders", orderService.findOrdersByCustomer(customer_id));
            req.setAttribute("customerInfo", customerService.findById(customer_id).get());
            req.getRequestDispatcher(JspHelper.getPath("ordersByCustomer")).forward(req, resp);
        } else if (req.getParameter("itemsId") != null) {
            Integer item_id = Integer.valueOf(req.getParameter("itemsId"));
            req.setAttribute("itemOrders", orderService.findOrdersByItems(item_id));
            req.setAttribute("itemInfo", itemService.findAll());
            req.setAttribute("customerInfo", customerService.findAll());
            req.getRequestDispatcher(JspHelper.getPath("ordersByItem")).forward(req, resp);
        }

    }
}
