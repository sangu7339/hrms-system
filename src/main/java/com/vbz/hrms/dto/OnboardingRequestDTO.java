package com.vbz.hrms.dto;

import lombok.Data;

@Data
public class OnboardingRequestDTO  {
	
//	private Long userId;
    private PersonalDetailsDTO personalDetailsDTO ;
    private JobDetailsDTO jobDetailsDTO;
    private SalaryDetailsDTO salaryDetailsDTO;
    private BankDetailsDTO bankDetailsDTO;
    private EmployeeStatutoryDetailsDTO employeeStatutoryDetailsDTO;

}
