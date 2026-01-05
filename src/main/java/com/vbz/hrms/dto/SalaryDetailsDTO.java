package com.vbz.hrms.dto;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class SalaryDetailsDTO {

    private BigDecimal ctc;
    private BigDecimal basic;
    private BigDecimal hra;
    private BigDecimal conveyanceAllowance;

    
}
