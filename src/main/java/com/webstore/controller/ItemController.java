package com.webstore.controller;

import com.webstore.model.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "/item";
    }
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setAttribute("items", itemService.findAll());
//        req.getRequestDispatcher(JspHelper.getPath("item"))
//                .forward(req, resp);
//    }
}
