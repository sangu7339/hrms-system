package com.vbz.hrms.Controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vbz.hrms.Service.SalaryCalculateService;
import com.vbz.hrms.model.SalaryCalculate;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/salary")
public class SalaryCalculaterController {
	
	@Autowired
	private final SalaryCalculateService salaryCalculateService;
	
	@PostMapping("/calculator")
	public ResponseEntity<String>calculatore(@RequestBody SalaryCalculate calculate, HttpSession session){
		try {
			String msg=salaryCalculateService.calculatoresalary(calculate, session);
			return ResponseEntity.status(HttpStatus.CREATED).body(msg);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/calculator/get")
	public ResponseEntity<List<SalaryCalculate>> getList() {
	    try {
	        List<SalaryCalculate> list = salaryCalculateService.getSalaryCalculator();
	        return ResponseEntity.ok(list);
	    } catch (Exception e) {
	        return ResponseEntity.badRequest()
	                .body(Collections.emptyList());
	    }
	}
}