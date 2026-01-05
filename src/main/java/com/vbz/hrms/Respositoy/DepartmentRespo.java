package com.vbz.hrms.Respositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.Department;

public interface DepartmentRespo extends JpaRepository<Department, Long> {

	boolean existsByDepartmentName(String departmentName);

}
