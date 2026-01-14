package com.vbz.hrms.Respositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.SalaryCalculate;

public interface SalaryCalculateRespo extends JpaRepository<SalaryCalculate, Long>  {

}
