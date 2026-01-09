package com.vbz.hrms.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vbz.hrms.Respositoy.LeaveRespo;
import com.vbz.hrms.Service.LeaveService;
import com.vbz.hrms.dto.LeaveDto;
import com.vbz.hrms.model.Leave;
import com.vbz.hrms.model.LeaveStatus;

import jakarta.servlet.http.HttpSession;
import lombok.Delegate;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LeaveController {
	
	private final LeaveService leaveService;
	
	public LeaveController(LeaveService leaveService) {
		this.leaveService=leaveService;
	}
	
	@PostMapping("/apply")
	public ResponseEntity<String>leaveApply(@RequestBody LeaveDto dto, HttpSession session){
	try {
		String msg=leaveService.applyForLeave(dto, session);
		return ResponseEntity.status(HttpStatus.CREATED).body(msg);
	}catch (Exception e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	}
	
	@PutMapping("/leave/edit/{id}")
	public ResponseEntity<String>editLeave(@RequestBody LeaveDto  dto, HttpSession session, @PathVariable Long id ){
		try {
			String msg=leaveService.editleave(dto, session, id);
			return ResponseEntity.status(HttpStatus.OK).body(msg);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	  @GetMapping("/list")
	    public ResponseEntity<List<Leave>> myLeaveList(HttpSession session) {

	        try {
	            List<Leave> leaves = leaveService.getMyLeaves(session);
	            return ResponseEntity.ok(leaves);
	           
	        } catch (Exception e) {
	            return ResponseEntity
	                    .status(HttpStatus.UNAUTHORIZED)
	                    .body(null);
	        }
	    }
	  @DeleteMapping("/leave/{id}")
	  public String deleteLeave(HttpSession session, @PathVariable("id") Long id){
		  try {
			  String msg=leaveService.deleteMyLeave(session, id);
			  
			  return msg;
		  }catch (Exception e) {
			return e.getMessage();
		}
	  }
	  
	  
	@GetMapping("leave/all/list")
	public  ResponseEntity<List<Leave>>getAllEmployeeLeaves(){
		try {
			List<Leave> leaves=leaveService.getAllLeaves();
			return ResponseEntity.status(HttpStatus.OK).body(leaves);
		}
	catch (Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	}

	@PutMapping("/leave/{id}/status")
	public ResponseEntity<String> approveOrRejectLeave(
	        @PathVariable("id") Long id,
	        @RequestParam LeaveStatus status,
	        HttpSession session) {

	    if (id == null) {
	        return ResponseEntity
	                .badRequest()
	                .body("Leave ID must not be null");
	    }

	    try {
	        String msg = leaveService.approveOrRejectLeave(id, status, session);
	        return ResponseEntity.ok(msg);
	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(e.getMessage());
	    }
	}

}
