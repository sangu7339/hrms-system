package com.vbz.hrms.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity

@Data
@Table(name = "monthly_salary")
public class MonthlySalary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalDay;
    private Long actualDay;

    private BigDecimal basic;
    private BigDecimal hra;
    private BigDecimal conveyanceAllowance;
    private BigDecimal grossSalary;
    private BigDecimal lop;

    private Integer month;
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;          

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generated_by", nullable = false)
    @JsonIgnore
    private User generatedBy; 
    
    
    
    
    
    
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
