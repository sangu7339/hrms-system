package com.vbz.hrms.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vbz.hrms.Respositoy.DepartmentRespo;
import com.vbz.hrms.Service.DeptService;
import com.vbz.hrms.dto.DepartmentDto;
import com.vbz.hrms.dto.DesignationDto;
import com.vbz.hrms.dto.OnboardingRequestDTO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/dept")
public class DeptController {
	
	private DeptService deptService;
	public DeptController(DeptService deptService) {
		this.deptService=deptService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<String>createDept(@RequestBody DepartmentDto dto, HttpSession session){
		try {
			String msg=deptService.createDepartment(dto, session);
			return ResponseEntity.status(HttpStatus.CREATED).body(msg);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	@PostMapping("/DesignationName")
  public ResponseEntity<String>creatingDesignation(@RequestBody DesignationDto dto, HttpSession session ){
	  try {
		  String msg=deptService.createDesignation(dto, session);
		  return ResponseEntity.status(HttpStatus.CREATED).body(msg);
	  }catch (Exception e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
	
  }
	
	@PostMapping("/onboarding")
	public ResponseEntity<String>onBoarding(@RequestBody OnboardingRequestDTO dto){
		try {
			String msg=deptService.empOnBoarding(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(msg);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
		
	
}
