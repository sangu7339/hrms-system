package com.vbz.hrms.Service;

import java.nio.channels.IllegalChannelGroupException;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import com.vbz.hrms.Respositoy.LeaveRespo;
import com.vbz.hrms.Respositoy.UserResp;
import com.vbz.hrms.dto.LeaveDto;
import com.vbz.hrms.model.Leave;
import com.vbz.hrms.model.LeaveStatus;
import com.vbz.hrms.model.User;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
@Service
public class LeaveServiceImp implements LeaveService {
	
	private final UserResp userResp;
	private final LeaveRespo leaveRespo;

	public LeaveServiceImp(UserResp userResp, LeaveRespo leaveRespo) {
		this.userResp=userResp;
		this.leaveRespo=leaveRespo;
	}

	@Override
	public String applyForLeave(LeaveDto dto, HttpSession session) {

		Long loggedInUserId=(Long)session.getAttribute("LOGGED_IN_USER_ID");
		
		if(loggedInUserId==null) {
			throw new RuntimeException("user not loggin in");
		}
		User user =userResp.findById(loggedInUserId)
				.orElseThrow(()->new RuntimeException("use not fount"));
		Long leaveDays=ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate())+1;
		System.out.println(leaveDays);
		 if (leaveDays > 3) {
		        throw new IllegalArgumentException("Leave cannot be more than 3 days");
		    }
		Leave leave=new Leave();
		leave.setDays(leaveDays);
		leave.setLeaveType(dto.getLeaveType());
		leave.setStartDate(dto.getStartDate());
		leave.setEndDate(dto.getEndDate());
		leave.setUser(user);
		leave.setReason(dto.getReason());
		leaveRespo.save(leave);
		
		return "Leave Applyed successfully";
	}

	@Override
	public String editleave(LeaveDto dto, HttpSession session, Long id) {
		Long LoggedInUserId=(Long)session.getAttribute("LOGGED_IN_USER_ID");
		if(LoggedInUserId==null) {
			throw new IllegalStateException("user not login");
		}
		User user=userResp.findById(LoggedInUserId)
				.orElseThrow(()->new EntityNotFoundException("user not found"));
		Leave leave=leaveRespo.findById(id)
				.orElseThrow(()->new EntityNotFoundException("leave not found"));
			
		if(!leave.getUser().getId().equals(user.getId())) {
			throw new IllegalArgumentException("You are not allowed to edit this leave");
		}
		Long days =ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate())+1;
		if(days>3) {
			throw new IllegalArgumentException("Leave cannot be more than 3 days");
		}
		if(leave.getLeaveStatus().equals("PENDING")) {
		 throw new IllegalStateException("Only pending leave can be edited");
		}
		leave.setDays(days);
		leave.setLeaveType(dto.getLeaveType());
		leave.setEndDate(dto.getEndDate());
		leave.setStartDate(dto.getStartDate());
		leave.setReason(dto.getReason());
		leaveRespo.save(leave);
		
		return "Leave updated successully";
	}

	@Override
	public List<Leave> getMyLeaves(HttpSession session) {

	    Long loggedInUserId = (Long) session.getAttribute("LOGGED_IN_USER_ID");

	    if (loggedInUserId == null) {
	        throw new IllegalStateException("User not logged in");
	    }
	    User user = userResp.findById(loggedInUserId)
	            .orElseThrow(() -> new EntityNotFoundException("User not found"));
	    List<Leave> leaves = leaveRespo.findByUser(user);

	    return leaves.stream()
	            .map(leave -> {
	            	Leave dto = new Leave();
	            	dto.setId(leave.getId());
	                dto.setLeaveType(leave.getLeaveType());  
	                dto.setStartDate(leave.getStartDate());
	                dto.setEndDate(leave.getEndDate());
	                dto.setDays(leave.getDays());
	                dto.setReason(leave.getReason());
	                dto.setLeaveStatus(leave.getLeaveStatus());
	                dto.setCreatedOn(leave.getCreatedOn());
	                dto.setUpdatedOn(leave.getUpdatedOn());
	                dto.setDeletedOn(leave.getDeletedOn());
	                return dto;
	            })
	            .toList();
	}

	@Override
	public String deleteMyLeave(HttpSession session, Long id) {
		Long loggedinuserid=(Long)session.getAttribute("LOGGED_IN_USER_ID");
		
		if(loggedinuserid==null) {
			throw new IllegalStateException("user not login");
		}
		
		User user=userResp.findById(loggedinuserid)
				.orElseThrow(()-> new EntityNotFoundException("user not found"));
		
			Leave leave=leaveRespo.findById(id)
					.orElseThrow(()-> new EntityNotFoundException("leave not found"));
			
			if(!"PENDING".equals(leave.getLeaveStatus())) {
				throw new IllegalStateException("only pending status can delete");
			}
			
			leaveRespo.delete(leave);
		return "leave delete succefully";
	}

	@Override
	public List<Leave> getAllLeaves() {
		
		return leaveRespo.findAll();
	}

	@Override
	public String approveOrRejectLeave(Long id, LeaveStatus status, HttpSession session) {

	    Long loggedinuserid = (Long) session.getAttribute("LOGGED_IN_USER_ID");
	    if (loggedinuserid == null) {
	        throw new IllegalStateException("User not logged in");
	    }

	    User changer = userResp.findById(loggedinuserid)
	            .orElseThrow(() -> new EntityNotFoundException("User not found"));

	    Leave leave = leaveRespo.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Leave not found"));
	    if (leave.getLeaveStatus() != LeaveStatus.PENDING) {
	        throw new IllegalStateException("Only pending leave can be approved or rejected");
	    }

	    leave.setLeaveStatus(status);  
	    leave.setStatusChanger(changer);  

	    leaveRespo.save(leave);

	    return "Leave " + status.name().toLowerCase() + " successfully";
	}


	
	


}
