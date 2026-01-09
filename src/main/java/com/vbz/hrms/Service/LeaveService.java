package com.vbz.hrms.Service;

import java.util.List;

import com.vbz.hrms.dto.LeaveDto;
import com.vbz.hrms.model.Leave;

import jakarta.servlet.http.HttpSession;

public interface LeaveService {

	String applyForLeave(LeaveDto dto, HttpSession session);

	String editleave(LeaveDto dto, HttpSession session, Long id);

	List<Leave> getMyLeaves(HttpSession session);

	String deleteMyLeave(HttpSession session, Long id);

	
	
	
}
