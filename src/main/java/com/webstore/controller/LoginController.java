package com.webstore.controller;

import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.dto.UserSessionCreateDto;
import com.webstore.model.dto.UserSessionReadDto;
import com.webstore.model.service.CustomerService;
import com.webstore.model.service.UserSessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final CustomerService customerService;
    private final UserSessionService userSessionService;

    @GetMapping
    public String getPage(@ModelAttribute("customer") CustomerReadDto customerReadDto) {
        return "/login";
    }

    @PostMapping
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        customerService.login(email, password)
                .ifPresent(customerReadDto -> loginSuccess(customerReadDto,request,response));
        return "redirect:/items";
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
       // resp.sendRedirect("/shop/orders?customerId=" + customerReadDto.getCustomer_id());
    }

    @SneakyThrows
    private void loginWrong(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/shop/login?error&email=" + req.getParameter("email"));
    }
}
