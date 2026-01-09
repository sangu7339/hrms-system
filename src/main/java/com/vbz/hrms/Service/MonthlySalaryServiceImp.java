package com.vbz.hrms.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vbz.hrms.Respositoy.SalaryDetailsRespo;
import com.vbz.hrms.Respositoy.UserResp;
import com.vbz.hrms.dto.MonthlySalaryDto;
import com.vbz.hrms.model.MonthlySalary;
import com.vbz.hrms.model.SalaryDetails;
import com.vbz.hrms.model.User;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MonthlySalaryServiceImp implements MonthlySalaryService {
	
	private final UserResp userResp;
	
	public MonthlySalaryServiceImp(UserResp userResp) {
		this.userResp=userResp;
	}

	@Override
	public String generateMonthlySalary(MonthlySalaryDto dto) {
		User user=userResp.findById(dto.getUserId())
				.orElseThrow(()->new EntityNotFoundException("user not found"));
		
		return null;
	}

}
