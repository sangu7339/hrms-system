package com.vbz.hrms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Entity
@Data
public class Calender {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String type;
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate date;
	

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
