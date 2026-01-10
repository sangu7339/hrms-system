package com.vbz.hrms.dto;

import lombok.Data;

@Data
public class MonthlySalaryDto {

    private Long totalDay;
    private Long actualDay;
    private Long userId;

    private Integer month;
    private Integer year;
}
