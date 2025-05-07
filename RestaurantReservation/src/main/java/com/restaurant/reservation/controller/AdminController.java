package com.restaurant.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.restaurant.reservation.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/home")
    public String adminHome(Model model) {
        model.addAttribute("reservations", adminService.findAllReservations());
        model.addAttribute("openingHour", adminService.getOpeningHour());
        model.addAttribute("closingHour", adminService.getClosingHour());
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

    @PostMapping("/config/updateHours")
    public String updateHours(String openingHour, String closingHour) {
        // Por ahora lo almacenamos en memoria (puedes mejorarlo con persistencia si quieres)
        adminService.setOpeningHour(openingHour);
        adminService.setClosingHour(closingHour);
        return "redirect:/admin/home";
    }

}