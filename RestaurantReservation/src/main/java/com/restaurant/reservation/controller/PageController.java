package com.restaurant.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller to handle navigation to public and customer pages.
 */
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

    @GetMapping("/customer/home")
    public String customerHome() {
        return "customer/customer"; // apunta a templates/customer/customer.html
    }
}
