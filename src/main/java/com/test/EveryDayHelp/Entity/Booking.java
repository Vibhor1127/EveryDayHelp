package com.test.EveryDayHelp.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String serviceType;

    @Column
    private String duration; // LTS or STS

    @Column
    private Integer amount;

    @Column
    private String email;

    @Column
    private String adminMessage; // nullable by default

    @Column
    private String timeSlot; // Admin assigned time slot

    @Column
    private String status = "Pending"; // Pending, Activated, Rejected

    @Column
    private LocalDateTime date;

    public Booking() {
        super();
    }

    public Booking(Integer id, String serviceType, String duration, Integer amount, String email,
                   String adminMessage, String timeSlot, String status, LocalDateTime date) {
        super();
        this.id = id;
        this.serviceType = serviceType;
        this.duration = duration;
        this.amount = amount;
        this.email = email;
        this.adminMessage = adminMessage;
        this.timeSlot = timeSlot;
        this.status = status;
        this.date = date;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminMessage() {
        return adminMessage;
    }

    public void setAdminMessage(String adminMessage) {
        this.adminMessage = adminMessage;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}