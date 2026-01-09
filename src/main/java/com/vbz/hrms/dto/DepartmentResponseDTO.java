package com.vbz.hrms.dto;

public class DepartmentResponseDTO {

    private Long id;
    private String departmentName;

    public DepartmentResponseDTO(Long id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
