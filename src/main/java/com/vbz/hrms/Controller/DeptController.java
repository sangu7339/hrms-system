package com.vbz.hrms.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vbz.hrms.Respositoy.DepartmentRespo;
import com.vbz.hrms.Service.DeptService;
import com.vbz.hrms.dto.DepartmentDto;
import com.vbz.hrms.dto.DesignationDto;
import com.vbz.hrms.dto.OnboardingRequestDTO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/dept")
@CrossOrigin(origins = "http://localhost:5173")
public class DeptController {
	
	private DeptService deptService;
	public DeptController(DeptService deptService) {
		this.deptService=deptService;
	}
	
	@PostMapping("hr/create")
	public ResponseEntity<String>createDept(@RequestBody DepartmentDto dto, HttpSession session){
		try {
			String msg=deptService.createDepartment(dto, session);
			return ResponseEntity.status(HttpStatus.CREATED).body(msg);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	@PostMapping("hr/DesignationName")
  public ResponseEntity<String>creatingDesignation(@RequestBody DesignationDto dto, HttpSession session ){
	  try {
		  String msg=deptService.createDesignation(dto, session);
		  return ResponseEntity.status(HttpStatus.CREATED).body(msg);
	  }catch (Exception e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	
  }
	
	@PostMapping("hr/onboarding")
	public ResponseEntity<String>onBoarding(@RequestBody OnboardingRequestDTO dto){
		try {
			String msg=deptService.empOnBoarding(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(msg);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	@GetMapping("hr/emp/search")
	public ResponseEntity<?>searchEmployee(@RequestParam String value){
		try {
			return ResponseEntity.ok(deptService.searchEmployee(value));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	
	@PutMapping("hr/employee/edit")
	public ResponseEntity<?> editEmployee(
	        @RequestParam Long userId,
	        @RequestBody OnboardingRequestDTO dto) {

	    try {
	        return ResponseEntity.ok(deptService.editEmployee(userId, dto));
	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(e.getMessage());
	    }
	}
	@GetMapping("employee/emp/me")
	public ResponseEntity<?> getMyProfile(HttpSession session) {
	    try {
	        return ResponseEntity.ok(deptService.getLoggedInEmployeeDetails(session));
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}

	
}
