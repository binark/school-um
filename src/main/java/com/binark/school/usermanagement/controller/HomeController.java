package com.binark.school.usermanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home() {
        return "The user management module";
    }

    @GetMapping("public")
    public String publicRoute() {
        return "The public route";
    }

    @GetMapping("admin")
    public String adminRoute() {
        return "The admin route";
    }

    @GetMapping("user")
    public String userRoute() {
        return "The user route";
    }

    @GetMapping("keycloak")
    public String keycloakRoute() {
        return "The keycloak route";
    }
}
