package servlets;

import dao.ItemDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.ItemReadMapper;
import org.hibernate.SessionFactory;
import service.ItemService;
import util.ConnectionUtil;
import util.JspHelper;

import java.io.IOException;

@WebServlet("/items")
public class ItemsServlet extends HttpServlet {
    private ItemService itemService;

    @Override
    public void init() throws ServletException {
        SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
        itemService = new ItemService(
                new ItemDao(),
                new ItemReadMapper(),
                sessionFactory
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("items", itemService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("item"))
                .forward(req, resp);
    }
}
