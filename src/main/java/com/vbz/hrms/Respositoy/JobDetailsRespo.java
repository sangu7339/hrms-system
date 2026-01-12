package com.vbz.hrms.Respositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vbz.hrms.model.JobDetails;
import com.vbz.hrms.model.User;

public interface JobDetailsRespo extends JpaRepository<JobDetails, Long> {
	@Query("""
			   SELECT j FROM JobDetails j
			   WHERE LOWER(j.department.departmentName) LIKE LOWER(CONCAT('%', :value, '%'))
			      OR LOWER(j.designation.designationName) LIKE LOWER(CONCAT('%', :value, '%'))
			""")
			Optional<JobDetails> findByDepartmentOrDesignation(@Param("value") String value);

			Optional<JobDetails> findByUser(User user);
}
