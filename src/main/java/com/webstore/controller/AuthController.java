package com.webstore.controller;

import com.webstore.model.dto.CustomerCreateDto;
import com.webstore.model.dto.CustomerReadDto;
import com.webstore.model.dto.LoginRequestDto;
import com.webstore.model.service.CustomerService;
import com.webstore.model.service.LoginService;
import com.webstore.model.service.MyUserDetailsService;
import com.webstore.model.util.JwtTokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class AuthController {
    private final LoginService loginService;
    private final CustomerService customerService;

    @GetMapping(value = "/check-auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            String email = (String) authentication.getPrincipal();
            CustomerReadDto customerReadDto = customerService.findByEmail(email).get();
            return ResponseEntity.ok().body(customerReadDto);
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        return loginService.login(loginRequestDto, httpServletResponse);
    }
    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerReadDto registration(@RequestBody CustomerCreateDto customerCreateDto) {
        return customerService.createUser(customerCreateDto);
    }
}
