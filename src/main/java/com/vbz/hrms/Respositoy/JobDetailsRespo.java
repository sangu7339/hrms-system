package com.vbz.hrms.Respositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.JobDetails;

public interface JobDetailsRespo extends JpaRepository<JobDetails, Long> {

}
