package com.vbz.hrms.Service;

import com.vbz.hrms.dto.DepartmentDto;
import com.vbz.hrms.dto.DesignationDto;
import com.vbz.hrms.dto.OnboardingRequestDTO;

import jakarta.servlet.http.HttpSession;

public interface DeptService {

	public String createDepartment(DepartmentDto dto, HttpSession session);

	 String createDesignation(DesignationDto dto, HttpSession session);

	 String empOnBoarding(OnboardingRequestDTO dto);
		
	
	

	 


}
