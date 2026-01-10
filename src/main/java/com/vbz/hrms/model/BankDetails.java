package com.vbz.hrms.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "bank_details")
public class BankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bankName;
    private String accountNumber;
    private String ifsc;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    
    
    private LocalDateTime createdOn;

   
    private LocalDateTime updatedOn;

   
    private LocalDateTime deletedOn;

   
    @PrePersist
    public void onCreate() {
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedOn = LocalDateTime.now();
    }

    public void markDeleted() {
        this.deletedOn = LocalDateTime.now();
    }
}
