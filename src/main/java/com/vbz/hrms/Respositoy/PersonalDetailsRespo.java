package com.vbz.hrms.Respositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vbz.hrms.model.PersonalDetails;
import com.vbz.hrms.model.User;

public interface PersonalDetailsRespo extends JpaRepository<PersonalDetails, Long> {
	Optional<PersonalDetails> findByFirstNameIgnoreCase(String firstName);

	Optional<PersonalDetails> findByLastNameIgnoreCase(String lastName);

	@Query("""
	   SELECT p FROM PersonalDetails p
	   WHERE LOWER(CONCAT(p.firstName, ' ', p.lastName)) = LOWER(:fullName)
	""")
	Optional<PersonalDetails> findByFullNameIgnoreCase(@Param("fullName") String fullName);

	Optional<PersonalDetails> findByUser(User user);



}
