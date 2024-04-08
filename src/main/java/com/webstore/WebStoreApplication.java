package com.webstore;

import com.webstore.model.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class WebStoreApplication {
    public static void main(String[] args) {
        var a = SpringApplication.run(WebStoreApplication.class, args);
        System.out.println(a.getBean(CustomerService.class));
        String property = a.getEnvironment().getProperty("Gay");
        System.out.println(property);
    }
}

