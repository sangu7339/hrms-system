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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vbz.hrms.Service.CalenderService;
import com.vbz.hrms.model.Calender;

@RestController
 @CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("calender")
public class CalenderController {
	
	private final CalenderService calenderService ;
	
	public CalenderController(CalenderService calenderService) {
		this.calenderService=calenderService;
	}
	
	@PostMapping
	public ResponseEntity<String>createCalenderDate(@RequestBody Calender calender){
		try {
			String msg=calenderService.creatingDate(calender);
			return ResponseEntity.status(HttpStatus.CREATED).body(msg);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public String deletingCalender(@PathVariable Long id) {
		try {
			String msg=calenderService.deleteCalender(id);
			return msg;
		}catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@GetMapping
	public ResponseEntity<?>getCalender(){
		try {
			List<Calender>list=calenderService.getlist();
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	@PutMapping("/{id}")
	public String editCalinder(@RequestBody Calender calender, @PathVariable Long id) {
		try {
			String msg=calenderService.editClaender(calender, id);
			return msg;
		}catch (Exception e) {
		return e.getMessage();
		}
	}

}
