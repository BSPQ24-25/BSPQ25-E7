package com.restaurant.reservation.controller;

import com.restaurant.reservation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/home")
    public String adminHome(Model model) {
        model.addAttribute("reservations", adminService.findAllReservations());
        return "admin/admin";
    }

    @PostMapping("/reservations/{id}/cancel")
    public String cancelReservation(@PathVariable Long id) {
        adminService.cancelReservation(id);
        return "redirect:/admin/home";
    }

    @PostMapping("/reservations/{id}/confirm")
    public String confirmReservation(@PathVariable Long id) {
        adminService.confirmReservation(id);
        return "redirect:/admin/home";
    }

    @GetMapping("/config-restaurant")
    public String getRestaurantConfigPage(Model model) {
        model.addAttribute("lunchHours", adminService.findLunchHours()); // Example value
        model.addAttribute("dinnerHours", adminService.findDinnerHours()); // Example value
        return "admin/restaurant_config";
    }

}