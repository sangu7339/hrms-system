package com.vbz.hrms.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vbz.hrms.Respositoy.AnnocementRespo;
import com.vbz.hrms.Respositoy.UserResp;
import com.vbz.hrms.model.Annocement;
import com.vbz.hrms.model.User;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
@Service
public class AnnocementServiceImp implements AnnocementService {

	
	private final AnnocementRespo annocementRespo;
	private final UserResp userResp;
	
	public AnnocementServiceImp(AnnocementRespo annocementRespo, UserResp userResp) {
		this.annocementRespo=annocementRespo;
		this.userResp=userResp;
	}
	
	@Override
	public String annocementcreate(Annocement annocement, HttpSession session) {
		 Long userId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
	        if (userId == null) {
	            throw new RuntimeException("User not logged in");
	        }

	        User user = userResp.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));
		annocement.setCreadtedBy(user);
		annocementRespo.save(annocement);
		return "Created";
	}

	@Override
	public String edit(Annocement annocement, HttpSession session, Long id) {
		 Long userId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
		  if (userId == null) {
	            throw new RuntimeException("User not logged in");
	        }

	        User user = userResp.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));
	        Annocement existing = annocementRespo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Announcement not found"));
	        existing.setCreadtedBy(user);
	        existing.setDescription(annocement.getDescription());
	        existing.setTitle(annocement.getTitle());
	        annocementRespo.save(existing);
	        
	     
		return "update Successfully";
	}

	@Override
	public List<Annocement> getAll() {
		List<Annocement>list=annocementRespo.findAll();
		if(list.isEmpty()) {
			throw new NullPointerException("Annocement is empty");
		}
		
		return list;
	}

}
