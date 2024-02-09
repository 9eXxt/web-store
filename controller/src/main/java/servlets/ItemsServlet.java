package servlets;

import dao.ItemsDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ItemsService;
import util.JspHelper;

import java.io.IOException;

@WebServlet("/items")
public class ItemsServlet extends HttpServlet {
    private ItemsService itemsService;

    @Override
    public void init() throws ServletException {
        itemsService = new ItemsService(
                new ItemsDao()
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("items", itemsService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("items"))
                .forward(req, resp);
    }
}
