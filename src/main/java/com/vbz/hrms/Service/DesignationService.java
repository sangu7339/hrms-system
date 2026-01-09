package com.vbz.hrms.Service;

import java.util.List;
import com.vbz.hrms.dto.DesignationDto;
import com.vbz.hrms.dto.DesignationResponseDTO;

import jakarta.servlet.http.HttpSession;

public interface DesignationService {

    String createDesignation(DesignationDto dto, HttpSession session);

    List<DesignationResponseDTO> getAllDesignations();
}
