package com.restaurant.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "common/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "common/register";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin/admin"; // apunta a templates/admin/admin.html
    }

    @GetMapping("/customer/home")
    public String customerHome() {
        return "customer/customer"; // apunta a templates/customer/customer.html
    }
}
