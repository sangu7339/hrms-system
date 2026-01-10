package com.vbz.hrms.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MonthlySalaryResponseDto {
    private Long salaryId;
    private Long employeeId;
    private String employeeName;
    private Long totalDay;
    private Long actualDay;
    private BigDecimal basic;
    private BigDecimal hra;
    private BigDecimal conveyanceAllowance;
    private BigDecimal grossSalary;
    private BigDecimal lop;
    private Integer month;
    private Integer year;
}

