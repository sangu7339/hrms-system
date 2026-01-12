package com.vbz.hrms.Respositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.EmployeeStatutoryDetails;
import com.vbz.hrms.model.User;

public interface EmployeeStatutoryDetailsRespo extends JpaRepository<EmployeeStatutoryDetails, Long> {

//	Optional<User> findByUser(User user);
	 Optional<EmployeeStatutoryDetails> findByUser(User user);

}
