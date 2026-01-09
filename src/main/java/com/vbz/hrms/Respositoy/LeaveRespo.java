package com.vbz.hrms.Respositoy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.Leave;
import com.vbz.hrms.model.User;

public interface LeaveRespo extends JpaRepository<Leave, Long> {

	 List<Leave> findByUser(User user);

	   
}
