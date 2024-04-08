package com.webstore.controller;


import com.webstore.model.dto.CustomerCreateDto;
import com.webstore.model.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final CustomerService customerService;

    @GetMapping
    public String getPage(@ModelAttribute("customer") CustomerCreateDto customerCreateDto) {
        return "/registration";
    }

    @PostMapping
   // @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute CustomerCreateDto customerCreateDto,
                         RedirectAttributes redirectAttributes) {
//        if (true) {
//            redirectAttributes.addFlashAttribute("customer", customerCreateDto);
//            return "redirect:/registration";
//        }
        customerService.createUser(customerCreateDto);
        return "redirect:login";
    }
}
