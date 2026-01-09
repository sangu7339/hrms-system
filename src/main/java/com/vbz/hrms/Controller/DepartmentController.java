package com.vbz.hrms.Controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.vbz.hrms.Service.DepartmentService;
import com.vbz.hrms.dto.DepartmentDto;
import com.vbz.hrms.dto.DepartmentResponseDTO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> create(
            @RequestBody DepartmentDto dto,
            HttpSession session) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createDepartment(dto, session));
    }

    @GetMapping
    public List<DepartmentResponseDTO> getAll() {
        return service.getAllDepartments();
    }
}
