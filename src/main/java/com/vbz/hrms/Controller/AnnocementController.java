package com.vbz.hrms.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vbz.hrms.Service.AnnocementService;
import com.vbz.hrms.model.Annocement;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("annocement")
public class AnnocementController {
	
	
	private final AnnocementService annocementService;
	
	
	public AnnocementController(AnnocementService annocementService) {
		this.annocementService=annocementService;
	}
	
	@PostMapping
	public String annocement(@RequestBody Annocement annocement, HttpSession session) {
		try {
			String msg=annocementService.annocementcreate(annocement, session);
			return msg;
		}catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@PutMapping("/{id}")
	public String edit(@RequestBody Annocement annocement, HttpSession session, @PathVariable Long id) {
		try {
			String msg=annocementService.edit(annocement, session, id);
			return msg;
		}catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GetMapping
	public ResponseEntity<?>getAll(){
		try {
			List<Annocement>list=annocementService.getAll();
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
