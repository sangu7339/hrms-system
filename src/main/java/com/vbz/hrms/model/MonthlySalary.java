package com.vbz.hrms.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
@Data
@Entity
public class MonthlySalary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long todalDay;
	private Long actualDay;
    private BigDecimal basic;
    private BigDecimal hra;
    private BigDecimal conveyanceAllowance;
    @OneToOne
    private User user;
   
}
