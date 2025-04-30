package com.restaurant.reservation.controller;


import com.restaurant.reservation.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/restaurant-config")
public class AdminRestaurantConfig {

    @Autowired
    private AdminService adminService;


    @PostMapping("/update-hours")
    public String updateAvailabilityHours(@RequestParam("availableHours") List<String> availableHours, Model model) {
        // Call the service to update the hours in the database
        adminService.updateHours(availableHours);
        model.addAttribute("lunchHours", availableHours.get(0)); // First value is lunch hours
        model.addAttribute("dinnerHours", availableHours.get(1)); // Second value is dinner hours
        return "admin/restaurant_config";
    }

}
