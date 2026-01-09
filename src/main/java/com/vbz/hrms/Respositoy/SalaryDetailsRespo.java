package com.vbz.hrms.Respositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.SalaryDetails;
import com.vbz.hrms.model.User;

public interface SalaryDetailsRespo extends JpaRepository<SalaryDetails, Long> {

	Optional<SalaryDetails>findByUser(User user);
	

}
