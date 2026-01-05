package com.vbz.hrms.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class JobDetailsDTO {

    private Long departmentId;
    private Long designationId;
    private String workLocation;
    private LocalDate dateOfJoining;

    
}
