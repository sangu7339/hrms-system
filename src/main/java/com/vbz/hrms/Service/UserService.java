package com.vbz.hrms.Service;

import com.vbz.hrms.dto.RoleReq;
import com.vbz.hrms.dto.UserRequestDto;
import com.vbz.hrms.dto.UserRoleRequestDto;
import com.vbz.hrms.model.Role_Master;
import com.vbz.hrms.model.User;
import com.vbz.hrms.model.User_Role;

import jakarta.servlet.http.HttpSession;

public interface UserService {
	
	public User addUser(UserRequestDto  dto);

 String login(UserRequestDto dto, HttpSession session);

 Role_Master createRole(RoleReq dto);

 User_Role assignRole(UserRoleRequestDto dto, HttpSession session);


	

}
