package com.vbz.hrms.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vbz.hrms.Respositoy.*;
import com.vbz.hrms.dto.DepartmentDto;
import com.vbz.hrms.dto.DesignationDto;
import com.vbz.hrms.dto.OnboardingRequestDTO;
import com.vbz.hrms.model.*;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class DeptServiceImp implements DeptService {

    private final DepartmentRespo departmentRespo;
    private final UserResp userResp;
    private final DesignationRespo designationRespo;
    private final JobDetailsRespo jobDetailsRespo;
    private final PersonalDetailsRespo personalDetailsRespo;
    private final BankDetailsRespo bankDetailsRespo;
    private final EmployeeStatutoryDetailsRespo employeeStatutoryDetailsRespo;
    private final SalaryDetailsRespo salaryDetailsRespo;
    private final Role_MatsreRespo roleMasterRespo;
    private final User_RoleRespo userRoleRespo;

    public DeptServiceImp(
            DepartmentRespo departmentRespo,
            UserResp userResp,
            DesignationRespo designationRespo,
            JobDetailsRespo jobDetailsRespo,
            PersonalDetailsRespo personalDetailsRespo,
            BankDetailsRespo bankDetailsRespo,
            EmployeeStatutoryDetailsRespo employeeStatutoryDetailsRespo,
            SalaryDetailsRespo salaryDetailsRespo,
            Role_MatsreRespo roleMasterRespo,
            User_RoleRespo userRoleRespo
    ) {
        this.departmentRespo = departmentRespo;
        this.userResp = userResp;
        this.designationRespo = designationRespo;
        this.jobDetailsRespo = jobDetailsRespo;
        this.personalDetailsRespo = personalDetailsRespo;
        this.bankDetailsRespo = bankDetailsRespo;
        this.employeeStatutoryDetailsRespo = employeeStatutoryDetailsRespo;
        this.salaryDetailsRespo = salaryDetailsRespo;
        this.roleMasterRespo = roleMasterRespo;
        this.userRoleRespo = userRoleRespo;
    }

    @Override
    public String createDepartment(DepartmentDto dto, HttpSession session) {

        Long loggedInUserId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
        if (loggedInUserId == null) {
            throw new RuntimeException("User not logged in");
        }

        User createdBy = userResp.findById(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        if (departmentRespo.existsByDepartmentName(dto.getDepartmentName())) {
            throw new RuntimeException("Department already exists");
        }

        Department dept = new Department();
        dept.setDepartmentName(dto.getDepartmentName());
        dept.setCreatedBy(createdBy);

        departmentRespo.save(dept);
        return "Department created successfully";
    }

    @Override
    public String createDesignation(DesignationDto dto, HttpSession session) {

        Long loggedInUserId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
        if (loggedInUserId == null) {
            throw new RuntimeException("User not logged in");
        }

        User createdBy = userResp.findById(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (designationRespo.existsByDesignationName(dto.getDesignationName())) {
            throw new RuntimeException("Designation already exists");
        }

        Designation designation = new Designation();
        designation.setDesignationName(dto.getDesignationName());
        designation.setCreatedBy(createdBy);

        designationRespo.save(designation);
        return "Designation created successfully";
    }

    @Override
    @Transactional
    public String empOnBoarding(OnboardingRequestDTO dto) {

        String prefix = "VPPL";

        Optional<User> lastUserOpt =
                userResp.findTopByUsernameStartingWithOrderByUsernameDesc(prefix);

        int nextNumber = 1;
        if (lastUserOpt.isPresent()) {
            String lastUsername = lastUserOpt.get().getUsername(); 
            String numberPart = lastUsername.substring(prefix.length());
            nextNumber = Integer.parseInt(numberPart) + 1;
        }

        String newUsername = prefix + String.format("%03d", nextNumber);

        User user = new User();
        user.setUsername(newUsername);
        user.setPassword("pass"); 
        user = userResp.save(user);

  
        Role_Master role = roleMasterRespo.findById(2L)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User_Role userRole = new User_Role();
        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setCreatedBy(user);
        userRoleRespo.save(userRole);

        PersonalDetails p = new PersonalDetails();
        p.setFirstName(dto.getPersonalDetailsDTO().getFirstName());
        p.setLastName(dto.getPersonalDetailsDTO().getLastName());
        p.setGender(dto.getPersonalDetailsDTO().getGender());
        p.setDob(dto.getPersonalDetailsDTO().getDob());
        p.setNationality(dto.getPersonalDetailsDTO().getNationality());
        p.setMaritalStatus(dto.getPersonalDetailsDTO().getMaritalStatus());
        p.setBloodGroup(dto.getPersonalDetailsDTO().getBloodGroup());
        p.setAadhaarNumber(dto.getPersonalDetailsDTO().getAadhaarNumber());
        p.setPanNumber(dto.getPersonalDetailsDTO().getPanNumber());
        p.setPhoneNumber(dto.getPersonalDetailsDTO().getPhoneNumber());
        p.setEmailId(dto.getPersonalDetailsDTO().getEmailId());
        p.setAddress1(dto.getPersonalDetailsDTO().getAddress1());
        p.setAddress2(dto.getPersonalDetailsDTO().getAddress2());
        p.setEmergencyContactName(dto.getPersonalDetailsDTO().getEmergencyContactName());
        p.setEmergencyPhoneNumber(dto.getPersonalDetailsDTO().getEmergencyPhoneNumber());
        p.setUser(user);
        personalDetailsRespo.save(p);

        BankDetails b = new BankDetails();
        b.setBankName(dto.getBankDetailsDTO().getBankName());
        b.setAccountNumber(dto.getBankDetailsDTO().getAccountNumber());
        b.setIfsc(dto.getBankDetailsDTO().getIfsc());
        b.setUser(user);
        bankDetailsRespo.save(b);

        EmployeeStatutoryDetails e = new EmployeeStatutoryDetails();
        e.setEsi(dto.getEmployeeStatutoryDetailsDTO().getEsi());
        e.setMin(dto.getEmployeeStatutoryDetailsDTO().getMin());
        e.setPfUan(dto.getEmployeeStatutoryDetailsDTO().getPfUan());
        e.setUser(user);
        employeeStatutoryDetailsRespo.save(e);

        JobDetails j = new JobDetails();
        j.setDateOfJoining(dto.getJobDetailsDTO().getDateOfJoining());
        j.setWorkLocation(dto.getJobDetailsDTO().getWorkLocation());
        j.setUser(user);

        Department department = departmentRespo.findById(
                dto.getJobDetailsDTO().getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Designation designation = designationRespo.findById(
                dto.getJobDetailsDTO().getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found"));

        j.setDepartment(department);
        j.setDesignation(designation);
        jobDetailsRespo.save(j);

        SalaryDetails s = new SalaryDetails();
        s.setBasic(dto.getSalaryDetailsDTO().getBasic());
        s.setHra(dto.getSalaryDetailsDTO().getHra());
        s.setConveyanceAllowance(dto.getSalaryDetailsDTO().getConveyanceAllowance());
        s.setCtc(dto.getSalaryDetailsDTO().getCtc());
        s.setUser(user);
        salaryDetailsRespo.save(s);

        return "Employee onboarded successfully with username " + newUsername;
    }
}
