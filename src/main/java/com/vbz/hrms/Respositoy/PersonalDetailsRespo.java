package com.vbz.hrms.Respositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.PersonalDetails;
import com.vbz.hrms.model.User;

public interface PersonalDetailsRespo extends JpaRepository<PersonalDetails, Long> {

	Optional<PersonalDetails> findByUser(User user);

	

}
