package com.vbz.hrms.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vbz.hrms.Service.ForgetPasswordServive;
import com.vbz.hrms.dto.ForgetPawwordDto;
import com.vbz.hrms.dto.ProfilePasswordDto;
import com.vbz.hrms.dto.SetPassword;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ForgetPawordController {
	
	private final ForgetPasswordServive forgetPasswordServive;
	public ForgetPawordController(ForgetPasswordServive forgetPasswordServive) {
		this.forgetPasswordServive=forgetPasswordServive;
	}
	
//	 @PostMapping("/forget-password")
	 	@PutMapping("/forget-password")
	    public ResponseEntity<String> forgetPassword(
	            @RequestBody ForgetPawwordDto dto) {

	        try {
	        	forgetPasswordServive.generatePassword(dto);
	            return ResponseEntity.ok(
	                " password sent to email: " + dto.getEmail()
	            );
	        } catch (Exception e) {
	            return ResponseEntity
	                    .internalServerError()
	                    .body("Failed to send password");
	        }
	    }

	@PutMapping("set")
	public ResponseEntity<String>restpawword(@RequestBody SetPassword dto){
		try {
			String msg=forgetPasswordServive.resetPasword(dto);
			return ResponseEntity.ok("Rest Password Successfully "+msg);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@PutMapping("password")
	public ResponseEntity<String>ProfilePassword(@RequestBody ProfilePasswordDto dto, HttpSession session){
		try {
			String msg=forgetPasswordServive.changePassword(dto, session);
			return ResponseEntity.status(HttpStatus.CREATED).body(msg);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
					
		}
	}
	
	@PostMapping("logout")
	public ResponseEntity<String>Logout(HttpSession session){
		session.invalidate();
		return  ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
	}

}
