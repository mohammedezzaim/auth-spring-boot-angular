package org.example.back.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ezzaim Mohammed
 **/
@RestController
@RequestMapping("admin")
public class DemoAmin {
    @GetMapping("/message")
    public String getMessage() {
        return "3IA Community : ADMIN";
    }
}
