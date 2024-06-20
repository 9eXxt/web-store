package com.webstore;

import com.webstore.model.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
@RequiredArgsConstructor
public class WebStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebStoreApplication.class, args);
    }
}

