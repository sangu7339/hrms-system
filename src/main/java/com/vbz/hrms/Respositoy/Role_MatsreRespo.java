package com.vbz.hrms.Respositoy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vbz.hrms.model.Role_Master;
import com.vbz.hrms.model.User;

public interface Role_MatsreRespo extends JpaRepository<Role_Master, Long> {

    boolean existsByRoleName(String roleName);

	Optional<User> findByRoleName(String roleName);

    // Optional<Role_Master> findByRoleName(String roleName); // optional, if needed
}
