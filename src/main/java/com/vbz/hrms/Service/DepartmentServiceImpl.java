package com.vbz.hrms.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vbz.hrms.Respositoy.DepartmentRespo;
import com.vbz.hrms.Respositoy.UserResp;
import com.vbz.hrms.dto.DepartmentDto;
import com.vbz.hrms.dto.DepartmentResponseDTO;
import com.vbz.hrms.model.Department;
import com.vbz.hrms.model.User;

import jakarta.servlet.http.HttpSession;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRespo departmentRespo;
    private final UserResp userResp;

    public DepartmentServiceImpl(DepartmentRespo departmentRespo, UserResp userResp) {
        this.departmentRespo = departmentRespo;
        this.userResp = userResp;
    }

    @Override
    public String createDepartment(DepartmentDto dto, HttpSession session) {

        Long userId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
        if (userId == null) throw new RuntimeException("User not logged in");

        User user = userResp.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (departmentRespo.existsByDepartmentName(dto.getDepartmentName())) {
            throw new RuntimeException("Department already exists");
        }

        Department dept = new Department();
        dept.setDepartmentName(dto.getDepartmentName());

        departmentRespo.save(dept);
        return "Department created successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentRespo.findAll()
                .stream()
                .map(d -> new DepartmentResponseDTO(
                        d.getId(), d.getDepartmentName()))
                .collect(Collectors.toList());
    }
}
