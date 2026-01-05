package com.vbz.hrms.Respositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.SalaryDetails;

public interface SalaryDetailsRespo extends JpaRepository<SalaryDetails, Long> {

}
