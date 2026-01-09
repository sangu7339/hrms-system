package com.vbz.hrms.Controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.vbz.hrms.Service.DesignationService;
import com.vbz.hrms.dto.DesignationDto;
import com.vbz.hrms.dto.DesignationResponseDTO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/designations")
public class DesignationController {

    private final DesignationService service;

    public DesignationController(DesignationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> create(
            @RequestBody DesignationDto dto,
            HttpSession session) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createDesignation(dto, session));
    }

    @GetMapping
    public List<DesignationResponseDTO> getAll() {
        return service.getAllDesignations();
    }
}
