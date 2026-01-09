package com.vbz.hrms.Service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.vbz.hrms.Email.EmailService;
import com.vbz.hrms.Respositoy.UserResp;
import com.vbz.hrms.dto.ForgetPawwordDto;
import com.vbz.hrms.dto.ProfilePasswordDto;
import com.vbz.hrms.dto.SetPassword;
import com.vbz.hrms.model.User;

import jakarta.servlet.http.HttpSession;

@Service
public class ForgetPasswordServive {

    private final EmailService emailService;
    private final UserResp userResp;

    public ForgetPasswordServive(EmailService emailService, UserResp userResp) {
        this.emailService = emailService;
        this.userResp = userResp;
    }

    public void generatePassword(ForgetPawwordDto dto) {

        User user = userResp.findByUsername(dto.getUsername())
                .orElseThrow(() -> new RuntimeException("Username not found"));
//        String email="patilsainath562@gmail.com";
               String email="vimal@venturebiz.in";
        if (!email.equals(dto.getEmail())) {
            throw new RuntimeException("Email does not match username");
        }
        
        		
        String password = "VBZ" + (new Random().nextInt(900000) + 100000);
       System.out.println(password);
//       int status=1;
       int status=0;
       user.setStatus(status);
       user.setPassword(password);
		userResp.save(user);
       
        emailService.sendPassword(
                dto.getEmail(),
                "Password Reset",
                "Hello " + dto.getUsername() + ",\n\n" +
                "Your  password is: " + password
        );
    }

	public String resetPasword(SetPassword dto) {
		
			User user=userResp.findByPassword(dto.getOldpassword())
					.orElseThrow(()-> new RuntimeException("password not found "));
		
//				if(user.getStatus()!=1) {
			if(user.getStatus()!=0) {
					throw new RuntimeException("user status fail because of status : 1");
				}
			
			int status=1;
			user.setStatus(status);
			user.setPassword(dto.getPassword());
			userResp.save(user);
		System.out.println(dto.getOldpassword() +" "+dto.getPassword());
		return user.getUsername();
	}

	public String changePassword(ProfilePasswordDto dto, HttpSession session) {
		Long loggedInUserId =(Long) session.getAttribute("LOGGED_IN_USER_ID");
		if(loggedInUserId==null) {
			throw new RuntimeException("User not logged in");
		}
		User user=userResp.findById(loggedInUserId)
				.orElseThrow(()-> new RuntimeException("user not found"));
			user.setPassword(dto.getPassword());
			userResp.save(user);
		
		return "password Update succeessfully";
	}
	


}
