package com.vbz.hrms.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "leaves")
public class Leave {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
     
	private String leaveType;
	private LocalDate startDate;
	private LocalDate endDate;
	@Enumerated(EnumType.STRING)
	private LeaveStatus leaveStatus;
	private Long days;
	private String reason;
	
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private LocalDateTime deletedOn;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User statusChanger;
	
	@PrePersist
	private void onCreated() {
		this.createdOn=LocalDateTime.now();
		this.updatedOn=LocalDateTime.now();
		this.leaveStatus=LeaveStatus.PENDING;
	}
	 @PreUpdate
	    public void onUpdate() {
	        this.updatedOn = LocalDateTime.now();
	    }

	    public void markDeleted() {
	        this.deletedOn = LocalDateTime.now();
	    }
}
