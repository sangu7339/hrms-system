package com.vbz.hrms.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class LeaveResponseDto {

    private Long leaveId;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long days;
    private String reason;
    private String leaveStatus;

    private Long employeeId;
    private String employeeName;

    private String statusChangedBy;
}
