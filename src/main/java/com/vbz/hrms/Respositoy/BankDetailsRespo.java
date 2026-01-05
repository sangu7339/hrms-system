package com.vbz.hrms.Respositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.BankDetails;

public interface BankDetailsRespo extends JpaRepository<BankDetails, Long> {

}
