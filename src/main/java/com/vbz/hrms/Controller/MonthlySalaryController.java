package com.vbz.hrms.Controller;
import com.vbz.hrms.dto.MonthlySalaryResponseDto;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vbz.hrms.Service.MonthlySalaryService;
import com.vbz.hrms.dto.MonthlySalaryDto;
import com.vbz.hrms.model.MonthlySalary;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/salary")
public class MonthlySalaryController {

    private final MonthlySalaryService monthlySalaryService;

    public MonthlySalaryController(MonthlySalaryService monthlySalaryService) {
        this.monthlySalaryService = monthlySalaryService;
    }

    @PostMapping("/month")
    public ResponseEntity<String> generateMonthlySalary(
            @RequestBody MonthlySalaryDto dto,
            HttpSession session) {

        try {
            String msg = monthlySalaryService.generateMonthlySalary(dto, session);
            return ResponseEntity.status(HttpStatus.CREATED).body(msg);


        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/month")
    public ResponseEntity<?> fetchYearAndMonth(
            @RequestParam Integer month,
            @RequestParam Integer year) {

        try {
            List<MonthlySalaryResponseDto> list =
                    monthlySalaryService.getsalaryListMonthAndYear(year, month);

            return ResponseEntity.ok(list);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
//    @GetMapping("/emp/my")
//    public ResponseEntity<?>fetchEmployeeAll(HttpSession session){
//    	try {
//    		List<MonthlySalary>list=monthlySalaryService.employeeSalary(session);
//    		return ResponseEntity.status(HttpStatus.OK).body(list);
//    	}catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//		}
//    }
}
