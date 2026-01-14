package com.vbz.hrms.Service;

import java.util.List;

import com.vbz.hrms.model.SalaryCalculate;

import jakarta.servlet.http.HttpSession;

public interface SalaryCalculateService {

	String calculatoresalary(SalaryCalculate calculate, HttpSession session);

	List<SalaryCalculate> getSalaryCalculator();

}
