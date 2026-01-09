package com.vbz.hrms.Service;

import java.util.List;
import com.vbz.hrms.dto.DepartmentDto;
import com.vbz.hrms.dto.DepartmentResponseDTO;

import jakarta.servlet.http.HttpSession;

public interface DepartmentService {

    String createDepartment(DepartmentDto dto, HttpSession session);

    List<DepartmentResponseDTO> getAllDepartments();
}
