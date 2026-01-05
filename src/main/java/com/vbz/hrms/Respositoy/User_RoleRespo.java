package com.vbz.hrms.Respositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vbz.hrms.model.User;
import com.vbz.hrms.model.User_Role;

public interface User_RoleRespo extends JpaRepository<User_Role, Long> {

	boolean existsByUserId(Long userId);
	Optional<User_Role>findByUser_id(Long UserId);


}