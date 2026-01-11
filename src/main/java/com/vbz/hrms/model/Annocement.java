package com.vbz.hrms.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class Annocement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String title;
	private String description;
	private LocalDateTime createdOn;
	private LocalDateTime updateOn;
	private LocalDateTime deleteOn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User creadtedBy;
	 
    @PrePersist
    public void onCreate() {
        this.createdOn = LocalDateTime.now();
        this.updateOn = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updateOn = LocalDateTime.now();
    }

    public void markDeleted() {
        this.deleteOn = LocalDateTime.now();
    }

}
