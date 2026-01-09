package com.vbz.hrms.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
public class LeaveDto {
	private String leaveType;
	private LocalDate startDate;
	private LocalDate endDate;
	private String reason;
}
