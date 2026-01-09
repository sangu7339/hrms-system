package com.vbz.hrms.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vbz.hrms.Respositoy.Role_MatsreRespo;
import com.vbz.hrms.Respositoy.UserResp;
import com.vbz.hrms.Respositoy.User_RoleRespo;
import com.vbz.hrms.Service.UserService;
import com.vbz.hrms.dto.RoleReq;
import com.vbz.hrms.dto.UserRequestDto;
import com.vbz.hrms.dto.UserRoleRequestDto;
import com.vbz.hrms.model.Role_Master;
import com.vbz.hrms.model.User;
import com.vbz.hrms.model.User_Role;
import com.vbz.hrms.util.ApiResponse;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;
	private UserResp userResp;

    public UserController(UserService userService, UserResp userResp) {
     
        this.userService=userService;
        this.userResp=userResp;
    }
  

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<User>> add(
            @RequestBody UserRequestDto dto) {

        try {
            User saved = userService.addUser(dto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "User created", saved));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<ApiResponse<String>>login(@RequestBody UserRequestDto dto, HttpSession session){
    	try {
    		String msg=userService.login(dto, session);
    		return ResponseEntity.ok(new ApiResponse<>(true, msg, null));
    	} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body( new ApiResponse<>(false, e.getMessage(), null));
		}
    	
    }
    @PostMapping("/role")
    public ResponseEntity<ApiResponse<Role_Master>>createRole(@RequestBody RoleReq dto){
    	try {
    		Role_Master role_Master=userService.createRole(dto);
    		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true,"Role created", role_Master ));
    	}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>(false, e.getMessage(), null));
		}
    }
    @PostMapping("/addrole")
    public ResponseEntity<ApiResponse<User_Role>> assignRole(@RequestBody UserRoleRequestDto dto,HttpSession session) {
        try {
            User_Role ur = userService.assignRole(dto, session);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Role assigned", ur));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }
    
 
    
}
    	
  
