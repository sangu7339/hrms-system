package com.vbz.hrms.Service;

import java.util.List;

import com.vbz.hrms.dto.MonthlySalaryDto;
import com.vbz.hrms.dto.MonthlySalaryResponseDto;
import com.vbz.hrms.model.MonthlySalary;

import jakarta.servlet.http.HttpSession;

public interface MonthlySalaryService {

	String generateMonthlySalary(MonthlySalaryDto dto, HttpSession session);

	List<MonthlySalaryResponseDto> getsalaryListMonthAndYear(Integer year, Integer month);

//	List<MonthlySalary> employeeSalary(HttpSession session);


}
