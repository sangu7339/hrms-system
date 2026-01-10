package com.vbz.hrms.Respositoy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.MonthlySalary;
import com.vbz.hrms.model.User;

public interface MonthlySalaryRespo extends JpaRepository<MonthlySalary, Long> {
	

    boolean existsByUserAndMonthAndYear(
            User user,
            Integer month,
            Integer year
    );

	List<MonthlySalary> findByMonthAndYear(Integer month, Integer year);

	List<MonthlySalary> findByUser(User user);
}
