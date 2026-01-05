package com.vbz.hrms.Respositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.PersonalDetails;

public interface PersonalDetailsRespo extends JpaRepository<PersonalDetails, Long> {

}
