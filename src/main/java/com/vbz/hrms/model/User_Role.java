package com.vbz.hrms.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "user_role")
public class User_Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleId", nullable = false)
    @JsonIgnore
    private Role_Master role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CreatedBy")
    @JsonIgnore
    private User createdBy;

    
    private LocalDateTime createdOn;

  
    private LocalDateTime updatedOn;

    private LocalDateTime deletedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DeletedBy")
    @JsonIgnore
    private User deletedBy;

    @PrePersist
    public void onCreate() {
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedOn = LocalDateTime.now();
    }

    public void markDeleted(User deletedBy) {
        this.deletedOn = LocalDateTime.now();
        this.deletedBy = deletedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role_Master getRole() {
        return role;
    }

    public void setRole(Role_Master role) {
        this.role = role;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public LocalDateTime getDeletedOn() {
        return deletedOn;
    }

    public User getDeletedBy() {
        return deletedBy;
    }
}

