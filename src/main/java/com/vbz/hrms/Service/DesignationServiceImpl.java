package com.vbz.hrms.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vbz.hrms.Respositoy.DesignationRespo;
import com.vbz.hrms.Respositoy.UserResp;
import com.vbz.hrms.dto.DesignationDto;
import com.vbz.hrms.dto.DesignationResponseDTO;
import com.vbz.hrms.model.Designation;
import com.vbz.hrms.model.User;

import jakarta.servlet.http.HttpSession;

@Service
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRespo designationRespo;
    private final UserResp userResp;

    public DesignationServiceImpl(DesignationRespo designationRespo, UserResp userResp) {
        this.designationRespo = designationRespo;
        this.userResp = userResp;
    }

    @Override
    public String createDesignation(DesignationDto dto, HttpSession session) {

        Long userId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
        if (userId == null) throw new RuntimeException("User not logged in");

        User user = userResp.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (designationRespo.existsByDesignationName(dto.getDesignationName())) {
            throw new RuntimeException("Designation already exists");
        }

        Designation d = new Designation();
        d.setDesignationName(dto.getDesignationName());
        designationRespo.save(d);

        return "Designation created successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public List<DesignationResponseDTO> getAllDesignations() {
        return designationRespo.findAll()
                .stream()
                .map(d -> new DesignationResponseDTO(
                        d.getId(), d.getDesignationName()))
                .collect(Collectors.toList());
    }
}
