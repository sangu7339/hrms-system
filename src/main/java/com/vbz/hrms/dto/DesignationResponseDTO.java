package com.vbz.hrms.dto;

public class DesignationResponseDTO {

    private Long id;
    private String designationName;

    public DesignationResponseDTO(Long id, String designationName) {
        this.id = id;
        this.designationName = designationName;
    }

    public Long getId() {
        return id;
    }

    public String getDesignationName() {
        return designationName;
    }
}
