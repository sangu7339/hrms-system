package com.vbz.hrms.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vbz.hrms.Respositoy.*;
import com.vbz.hrms.dto.BankDetailsDTO;
import com.vbz.hrms.dto.DepartmentDto;
import com.vbz.hrms.dto.DesignationDto;
import com.vbz.hrms.dto.EmployeeStatutoryDetailsDTO;
import com.vbz.hrms.dto.JobDetailsDTO;
import com.vbz.hrms.dto.OnboardingRequestDTO;
import com.vbz.hrms.dto.PersonalDetailsDTO;
import com.vbz.hrms.dto.SalaryDetailsDTO;
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
        p.setEmergencyContactRelation(dto.getPersonalDetailsDTO().getEmergencyContactRelation());
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

    @Override
    public OnboardingRequestDTO searchEmployee(String value) {

        User user = null;

      
        Optional<User> userOpt = userResp.findByUsernameIgnoreCase(value);
        if (userOpt.isPresent()) {
            user = userOpt.get();
        }
        if (user == null) {
            Optional<PersonalDetails> pOpt =
                    personalDetailsRespo.findByFirstNameIgnoreCase(value)
                    .or(() -> personalDetailsRespo.findByLastNameIgnoreCase(value));

            if (pOpt.isPresent()) {
                user = pOpt.get().getUser();
            }
        }

        if (user == null) {
            Optional<JobDetails> jOpt =
                    jobDetailsRespo.findByDepartmentOrDesignation(value);

            if (jOpt.isPresent()) {
                user = jOpt.get().getUser();
            }
        }

        if (user == null) {
            throw new RuntimeException("Employee not found");
        }


        OnboardingRequestDTO dto = new OnboardingRequestDTO();

        PersonalDetails p = personalDetailsRespo.findByUser(user).orElse(null);
        if (p != null) {
            PersonalDetailsDTO pdto = new PersonalDetailsDTO();
            pdto.setFirstName(p.getFirstName());
            pdto.setMiddleName(p.getMiddleName());
            pdto.setLastName(p.getLastName());
            pdto.setGender(p.getGender());
            pdto.setDob(p.getDob());
            pdto.setNationality(p.getNationality());
            pdto.setMaritalStatus(p.getMaritalStatus());
            pdto.setBloodGroup(p.getBloodGroup());
            pdto.setAadhaarNumber(p.getAadhaarNumber());
            pdto.setPanNumber(p.getPanNumber());
            pdto.setPhoneNumber(p.getPhoneNumber());
            pdto.setEmailId(p.getEmailId());
            pdto.setAddress1(p.getAddress1());
            pdto.setAddress2(p.getAddress2());
            pdto.setEmergencyContactName(p.getEmergencyContactName());
            pdto.setEmergencyPhoneNumber(p.getEmergencyPhoneNumber());

            dto.setPersonalDetailsDTO(pdto);
        }

        JobDetails j = jobDetailsRespo.findByUser(user).orElse(null);
        if (j != null) {
            JobDetailsDTO jdto = new JobDetailsDTO();
            jdto.setDepartmentId(j.getDepartment().getId());
            jdto.setDesignationId(j.getDesignation().getId());
            jdto.setWorkLocation(j.getWorkLocation());
            jdto.setDateOfJoining(j.getDateOfJoining());

            dto.setJobDetailsDTO(jdto);
        }

        SalaryDetails s = salaryDetailsRespo.findByUser(user).orElse(null);
        if (s != null) {
            SalaryDetailsDTO sdto = new SalaryDetailsDTO();
            sdto.setBasic(s.getBasic());
            sdto.setHra(s.getHra());
            sdto.setConveyanceAllowance(s.getConveyanceAllowance());
            sdto.setCtc(s.getCtc());

            dto.setSalaryDetailsDTO(sdto);
        }

        BankDetails b = bankDetailsRespo.findByUser(user).orElse(null);
        if (b != null) {
            BankDetailsDTO bdto = new BankDetailsDTO();
            bdto.setBankName(b.getBankName());
            bdto.setAccountNumber(b.getAccountNumber());
            bdto.setIfsc(b.getIfsc());

            dto.setBankDetailsDTO(bdto);
        }
        EmployeeStatutoryDetails e =
                employeeStatutoryDetailsRespo.findByUser(user).orElse(null);

        if (e != null) {
            EmployeeStatutoryDetailsDTO edto =
                    new EmployeeStatutoryDetailsDTO();
            edto.setPfUan(e.getPfUan());
            edto.setEsi(e.getEsi());
            edto.setMin(e.getMin());

            dto.setEmployeeStatutoryDetailsDTO(edto);
        }

        return dto;
    }

    @Override
    @Transactional
    public String editEmployee(Long userId, OnboardingRequestDTO dto) {

        User user = userResp.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PersonalDetails p = personalDetailsRespo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Personal details not found"));

        p.setFirstName(dto.getPersonalDetailsDTO().getFirstName());
        p.setMiddleName(dto.getPersonalDetailsDTO().getMiddleName());
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
        p.setEmergencyContactRelation(dto.getPersonalDetailsDTO().getEmergencyContactRelation());
        p.setEmergencyPhoneNumber(dto.getPersonalDetailsDTO().getEmergencyPhoneNumber());

        personalDetailsRespo.save(p);

        JobDetails j = jobDetailsRespo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Job details not found"));

        Department department = departmentRespo.findById(
                dto.getJobDetailsDTO().getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Designation designation = designationRespo.findById(
                dto.getJobDetailsDTO().getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found"));

        j.setDepartment(department);
        j.setDesignation(designation);
        j.setWorkLocation(dto.getJobDetailsDTO().getWorkLocation());
        j.setDateOfJoining(dto.getJobDetailsDTO().getDateOfJoining());

        jobDetailsRespo.save(j);
        SalaryDetails s = salaryDetailsRespo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Salary details not found"));

        s.setBasic(dto.getSalaryDetailsDTO().getBasic());
        s.setHra(dto.getSalaryDetailsDTO().getHra());
        s.setConveyanceAllowance(dto.getSalaryDetailsDTO().getConveyanceAllowance());
        s.setCtc(dto.getSalaryDetailsDTO().getCtc());

        salaryDetailsRespo.save(s);

        BankDetails b = bankDetailsRespo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Bank details not found"));

        b.setBankName(dto.getBankDetailsDTO().getBankName());
        b.setAccountNumber(dto.getBankDetailsDTO().getAccountNumber());
        b.setIfsc(dto.getBankDetailsDTO().getIfsc());

        bankDetailsRespo.save(b);

        EmployeeStatutoryDetails e = employeeStatutoryDetailsRespo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Statutory details not found"));

        e.setPfUan(dto.getEmployeeStatutoryDetailsDTO().getPfUan());
        e.setEsi(dto.getEmployeeStatutoryDetailsDTO().getEsi());
        e.setMin(dto.getEmployeeStatutoryDetailsDTO().getMin());

        employeeStatutoryDetailsRespo.save(e);

        return "Employee details updated successfully";
    }
    
    
    @Override
    public OnboardingRequestDTO getLoggedInEmployeeDetails(HttpSession session) {

        Long userId = (Long) session.getAttribute("LOGGED_IN_USER_ID");

        if (userId == null) {
            throw new RuntimeException("User not logged in");
        }

        User user = userResp.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        OnboardingRequestDTO dto = new OnboardingRequestDTO();

        personalDetailsRespo.findByUser(user)
                .ifPresent(pd -> {
                    PersonalDetailsDTO pDto = new PersonalDetailsDTO();
                    pDto.setFirstName(pd.getFirstName());
                    pDto.setLastName(pd.getLastName());
                    pDto.setGender(pd.getGender());
                    pDto.setDob(pd.getDob());
                    pDto.setPhoneNumber(pd.getPhoneNumber());
                    pDto.setEmailId(pd.getEmailId());
                    pDto.setAddress1(pd.getAddress1());
                    pDto.setAddress2(pd.getAddress2());
                    pDto.setAadhaarNumber(pd.getAadhaarNumber());
                    pDto.setBloodGroup(pd.getBloodGroup());
                    pDto.setNationality(pd.getNationality());
                    pDto.setMaritalStatus(pd.getMaritalStatus());
                    pDto.setPanNumber(pd.getPanNumber());
                    pDto.setEmergencyContactName(pd.getEmergencyContactName());
                    pDto.setEmergencyPhoneNumber(pd.getEmergencyPhoneNumber());
                    dto.setPersonalDetailsDTO(pDto);
                    
                });

        jobDetailsRespo.findByUser(user)
                .ifPresent(jd -> {
                    JobDetailsDTO jDto = new JobDetailsDTO();
                    jDto.setDepartmentId(jd.getDepartment().getId());
                    jDto.setDesignationId(jd.getDesignation().getId());
                    jDto.setWorkLocation(jd.getWorkLocation());
                    jDto.setDateOfJoining(jd.getDateOfJoining());
                    dto.setJobDetailsDTO(jDto);
                });

        salaryDetailsRespo.findByUser(user)
                .ifPresent(sd -> {
                    SalaryDetailsDTO sDto = new SalaryDetailsDTO();
                    sDto.setBasic(sd.getBasic());
                    sDto.setHra(sd.getHra());
                    sDto.setConveyanceAllowance(sd.getConveyanceAllowance());
                    sDto.setCtc(sd.getCtc());
                    dto.setSalaryDetailsDTO(sDto);
                });

        bankDetailsRespo.findByUser(user)
                .ifPresent(bd -> {
                    BankDetailsDTO bDto = new BankDetailsDTO();
                    bDto.setBankName(bd.getBankName());
                    bDto.setAccountNumber(bd.getAccountNumber());
                    bDto.setIfsc(bd.getIfsc());
                    dto.setBankDetailsDTO(bDto);
                });

      
        employeeStatutoryDetailsRespo.findByUser(user)
                .ifPresent(es -> {
                    EmployeeStatutoryDetailsDTO eDto = new EmployeeStatutoryDetailsDTO();
                    eDto.setPfUan(es.getPfUan());
                    eDto.setEsi(es.getEsi());
                    eDto.setMin(es.getMin());
                    dto.setEmployeeStatutoryDetailsDTO(eDto);
                });

        return dto;
    }


}
