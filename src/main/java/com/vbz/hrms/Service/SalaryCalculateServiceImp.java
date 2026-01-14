package com.vbz.hrms.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vbz.hrms.Respositoy.SalaryCalculateRespo;
import com.vbz.hrms.Respositoy.UserResp;
import com.vbz.hrms.model.SalaryCalculate;
import com.vbz.hrms.model.User;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SalaryCalculateServiceImp implements SalaryCalculateService {

	private final SalaryCalculateRespo salaryCalculateRespo;
	private final UserResp userResp;
	
	@Override
	public String calculatoresalary(SalaryCalculate calculate, HttpSession session) {
		Long loggedinuserid=(Long)session.getAttribute("LOGGED_IN_USER_ID");
		
		if(loggedinuserid==null) {
			throw new IllegalArgumentException("user not login");
		}
		
		User user=userResp.findById(loggedinuserid)
				.orElseThrow(()-> new EntityNotFoundException("user not found"));
		
		calculate.setUser(user);
		salaryCalculateRespo.save(calculate);
		
		return "Salary percentage master saved successfully";
	}

	@Override
	public List<SalaryCalculate> getSalaryCalculator() {
		List<SalaryCalculate>list=salaryCalculateRespo.findAll();
		if(list.isEmpty()) {
			throw new EntityNotFoundException("table is empty");
		}
		return list;
	}

}
