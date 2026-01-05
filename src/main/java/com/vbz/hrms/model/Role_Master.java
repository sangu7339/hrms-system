package com.vbz.hrms.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "role_master")
public class Role_Master {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  
    private String roleName;

   
    private Integer status;

 
    private LocalDateTime createdOn;

  
    private LocalDateTime updatedOn;

  
    private LocalDateTime deletedOn;

    @PrePersist
    public void onCreated() {
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
        this.status = 1;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedOn = LocalDateTime.now();
    }

    public void markDeleted() {
        this.deletedOn = LocalDateTime.now();
        this.status = 0;
    }
}
