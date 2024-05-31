package com.webstore.model.service;

import com.webstore.model.dto.LoginRequestDto;
import com.webstore.model.dto.LoginResponseDto;
import com.webstore.model.util.JwtTokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MyUserDetailsService myUserDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        UserDetails userDetails;
        try {
            userDetails = myUserDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body("User not found");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Incorrect password");
        }

        String token = jwtTokenUtils.generateToken(userDetails);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Только для https
        cookie.setPath("/");
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);

        return ResponseEntity.ok("Login successful");
    }

}
