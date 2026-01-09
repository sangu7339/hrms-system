package com.vbz.hrms.Respositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vbz.hrms.model.User;

public interface UserResp extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

	Optional<User> findByPassword(String password);
	 
	@Query("SELECT MAX(u.id) FROM User u")
    Long findMaxUserId();
}
