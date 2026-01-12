package com.vbz.hrms.Respositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.BankDetails;
import com.vbz.hrms.model.User;

public interface BankDetailsRespo extends JpaRepository<BankDetails, Long> {

	Optional<BankDetails> findByUser(User user);

}
