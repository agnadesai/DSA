package com.example.demo.controller;

import com.example.demo.model.RequestStatus;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import com.example.demo.service.VacationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    private final VacationService vacationService;

    public WebController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        if (user.getRole().name().equals("MANAGER")) {
            model.addAttribute("requests", vacationService.getAllRequests());
        } else {
            model.addAttribute("requests", vacationService.getEmployeeRequests(user));
            model.addAttribute("remainingDays", vacationService.getRemainingVacationDays(user));
        }
        return "home";
    }

    @GetMapping("/request/new")
    public String newRequestForm(Model model) {
        model.addAttribute("vacationRequest", new VacationRequest());
        return "request-form";
    }

    @PostMapping("/request/save")
    public String saveRequest(@AuthenticationPrincipal User user, @ModelAttribute VacationRequest request) {
        try {
            vacationService.createRequest(user, request);
            return "redirect:/";
        } catch (IllegalStateException | IllegalArgumentException e) {
            return "redirect:/request/new?error=" + e.getMessage();
        }
    }

    @GetMapping("/request/{id}")
    public String viewRequest(@PathVariable Long id, Model model) {
        try {
            VacationRequest request = vacationService.getRequestById(id);
            model.addAttribute("request", request);
            model.addAttribute("statuses", RequestStatus.values());
            return "request-detail";
        } catch (IllegalArgumentException e) {
            return "redirect:/?error=Request not found";
        }
    }

    @PostMapping("/request/{id}/process")
    public String processRequest(
            @PathVariable Long id,
            @AuthenticationPrincipal User manager,
            @RequestParam RequestStatus status) {
        try {
            vacationService.processRequest(id, manager, status);
            return "redirect:/request/" + id;
        } catch (IllegalStateException | IllegalArgumentException e) {
            return "redirect:/request/" + id + "?error=" + e.getMessage();
        }
    }
}
