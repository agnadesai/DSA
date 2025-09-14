package com.example.vacation_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee {
    
    @Id
    private String id;
    
    private int remainingVacationDays;
    
    private boolean isManager;

    public Employee() {
        this.remainingVacationDays = 30; // Default vacation days per year
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRemainingVacationDays() {
        return remainingVacationDays;
    }

    public void setRemainingVacationDays(int remainingVacationDays) {
        this.remainingVacationDays = remainingVacationDays;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }
}
