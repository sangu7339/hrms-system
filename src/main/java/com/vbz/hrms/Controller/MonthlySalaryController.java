package com.vbz.hrms.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vbz.hrms.Service.MonthlySalaryService;
import com.vbz.hrms.dto.MonthlySalaryDto;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/salary")
public class MonthlySalaryController {
	
	
	private final MonthlySalaryService monthlySalaryService;
	public MonthlySalaryController(MonthlySalaryService monthlySalaryService) {
		this.monthlySalaryService=monthlySalaryService;
		
	}
	
	public String MothlySalary(@RequestBody MonthlySalaryDto dto) {
		return null;
	}
	
	

}
