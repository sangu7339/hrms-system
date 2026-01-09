package com.vbz.hrms.Service;
import com.vbz.hrms.Respositoy.Role_MatsreRespo;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.vbz.hrms.model.Role_Master;
import com.vbz.hrms.Respositoy.Role_MatsreRespo;
import com.vbz.hrms.Respositoy.UserResp;
import com.vbz.hrms.Respositoy.User_RoleRespo;
import com.vbz.hrms.dto.RoleReq;
import com.vbz.hrms.dto.UserRequestDto;
import com.vbz.hrms.dto.UserRoleRequestDto;
import com.vbz.hrms.model.Role_Master;
import com.vbz.hrms.model.User;
import com.vbz.hrms.model.User_Role;

import jakarta.servlet.http.HttpSession;
@Service
public class UserServiceImpl implements UserService {
	
	private final User_RoleRespo user_RoleRespo;
	private final Role_MatsreRespo role_MatsreRespo;
	private final UserResp userResp;

	
	public UserServiceImpl(User_RoleRespo user_RoleRespo, Role_MatsreRespo role_MatsreRespo, UserResp userResp ) {
		this.role_MatsreRespo=role_MatsreRespo;
		this.user_RoleRespo=user_RoleRespo;
		this.userResp=userResp;
		
	}
	
	@Override
	public User addUser(UserRequestDto dto) {

	    if (userResp.findByUsername(dto.getUsername()).isPresent()) {
	        throw new RuntimeException("Username already exists");
	    }

	    User user = new User();
	    user.setUsername(dto.getUsername());
	    user.setPassword(dto.getPassword());

	    return userResp.save(user);
	}
//	   @Override
//	    public String login(UserRequestDto dto, HttpSession session) {
//
//	        User user = userResp
//	                .findByUsernameAndPassword(
//	                        dto.getUsername(), dto.getPassword())
//	                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
//	        User_Role user_Role=user_RoleRespo.findByUser_id(user.getId())
//	        		.orElseThrow(()->new RuntimeException("user not found in role"));
//	       
//	        session.setAttribute("LOGGED_IN_USER_ID", user.getId());
//	        return user_Role.getRole().getId()+",  status : "+user.getStatus();
//	       
//	    }
	   
	@Override
	public String login(UserRequestDto dto, HttpSession session) {

		User user = userResp.findByUsername(dto.getUsername())
			    .orElseThrow(() -> new RuntimeException("Invalid username"));

			if (!user.getPassword().equals(dto.getPassword())) {
			    throw new RuntimeException("Invalid password");
			}

	    User_Role userRole = user_RoleRespo.findByUser_id(user.getId())
	            .orElseThrow(() -> new RuntimeException("User role not assigned"));
	    
	    session.setAttribute("LOGGED_IN_USER_ID", user.getId());

	    return userRole.getRole().getId() + ",  status : " + user.getStatus();
	}

	   @Override
	   public Role_Master createRole(RoleReq dto) {

	       if (role_MatsreRespo.existsByRoleName(dto.getRoleName())) {
	           throw new RuntimeException("Role already exists: " + dto.getRoleName());
	       }

	       Role_Master role_Master = new Role_Master();
	       role_Master.setRoleName(dto.getRoleName());

	       return role_MatsreRespo.save(role_Master);
	   }

	   @Override
	    public User_Role assignRole(UserRoleRequestDto dto, HttpSession session) {

	        Long loggedInUserId =
	                (Long) session.getAttribute("LOGGED_IN_USER_ID");

	        if (loggedInUserId == null) {
	            throw new RuntimeException("User not logged in");
	        }
	   

	        User user = userResp.findById(dto.getUserId())
	                .orElseThrow(() -> new RuntimeException("User not found"));
	       
	        Role_Master role = role_MatsreRespo.findById(dto.getRoleId())
	                .orElseThrow(() -> new RuntimeException("Role not found"));

	        User createdBy = userResp.findById(loggedInUserId)
	                .orElseThrow(() -> new RuntimeException("Creator not found"));
	        if(user_RoleRespo.existsByUserId(dto.getUserId())) {
		    	throw new RuntimeException("User already exists" + user.getUsername());
		    }
	        
	       
	        User_Role userRole = new User_Role();
	        userRole.setUser(user);
	        userRole.setRole(role);
	        userRole.setCreatedBy(createdBy);
	        return user_RoleRespo.save(userRole);

	      
	    }

	

	
	
	

}
