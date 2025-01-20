package org.example.back.controller.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ezzaim Mohammed
 **/
@RestController
@RequestMapping("user")

public class DemoUser {

    @GetMapping("/message")
    public String getMessage() {
        return "3IA Community : USER";
    }

    @GetMapping("/hello")
    public String getHello() {
        return "3IA Community : hello";
    }
}
