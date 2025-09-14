package com.example.demo.controller;

import com.example.demo.model.RequestStatus;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import com.example.demo.service.UserService;
import com.example.demo.service.VacationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
public class WebController {

    private final VacationService vacationService;
    private final UserService userService;

    public WebController(VacationService vacationService, UserService userService) {
        this.vacationService = vacationService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model, Authentication auth) {
        User user = userService.getUserByUsername(auth.getName());
        if (user.getRole() == Role.MANAGER) {
            model.addAttribute("requests", vacationService.getAllRequests());
        } else {
            model.addAttribute("requests", vacationService.getUserRequests(user));
            model.addAttribute("remainingDays", vacationService.getRemainingVacationDays(user));
        }
        model.addAttribute("template", "home");
        return "layout";
    }

    @GetMapping("/request/new")
    public String newRequest(Model model) {
        model.addAttribute("vacationRequest", new VacationRequest());
        model.addAttribute("template", "request-form");
        return "layout";
    }

    @PostMapping("/request/save")
    public String saveRequest(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vacationStartDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vacationEndDate,
            @RequestParam(required = false) String comment,
            Authentication auth,
            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserByUsername(auth.getName());
            VacationRequest request = new VacationRequest();
            request.setVacationStartDate(LocalDateTime.of(vacationStartDate, LocalTime.MIN));
            request.setVacationEndDate(LocalDateTime.of(vacationEndDate, LocalTime.MAX));
            request.setComment(comment);
            
            vacationService.createRequest(user, request);
            redirectAttributes.addFlashAttribute("successMessage", "Vacation request created successfully");
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping("/request/{id}")
    public String viewRequest(@PathVariable Long id, Model model) {
        model.addAttribute("request", vacationService.getRequest(id));
        model.addAttribute("template", "request-detail");
        return "layout";
    }

    @PostMapping("/request/{id}/process")
    public String processRequest(@PathVariable Long id,
                               @RequestParam RequestStatus status,
                               Authentication auth,
                               RedirectAttributes redirectAttributes) {
        try {
            User manager = userService.getUserByUsername(auth.getName());
            vacationService.processRequest(id, manager, status);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Request " + status.toString().toLowerCase() + " successfully");
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/request/" + id;
    }
}
