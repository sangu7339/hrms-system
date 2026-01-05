package com.vbz.hrms.Service;

import org.springframework.stereotype.Service;

import com.vbz.hrms.Respositoy.BankDetailsRespo;
import com.vbz.hrms.Respositoy.DepartmentRespo;
import com.vbz.hrms.Respositoy.DesignationRespo;
import com.vbz.hrms.Respositoy.EmployeeStatutoryDetailsRespo;
import com.vbz.hrms.Respositoy.JobDetailsRespo;
import com.vbz.hrms.Respositoy.PersonalDetailsRespo;
import com.vbz.hrms.Respositoy.SalaryDetailsRespo;
import com.vbz.hrms.Respositoy.UserResp;
import com.vbz.hrms.dto.DepartmentDto;
import com.vbz.hrms.dto.DesignationDto;
import com.vbz.hrms.dto.OnboardingRequestDTO;
import com.vbz.hrms.model.BankDetails;
import com.vbz.hrms.model.Department;
import com.vbz.hrms.model.Designation;
import com.vbz.hrms.model.EmployeeStatutoryDetails;
import com.vbz.hrms.model.JobDetails;
import com.vbz.hrms.model.PersonalDetails;
import com.vbz.hrms.model.SalaryDetails;
import com.vbz.hrms.model.User;

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

  

    public DeptServiceImp(DepartmentRespo departmentRespo, UserResp userResp, DesignationRespo designationRespo,
			JobDetailsRespo jobDetailsRespo, PersonalDetailsRespo personalDetailsRespo,
			BankDetailsRespo bankDetailsRespo, EmployeeStatutoryDetailsRespo employeeStatutoryDetailsRespo,
			SalaryDetailsRespo salaryDetailsRespo) {
		super();
		this.departmentRespo = departmentRespo;
		this.userResp = userResp;
		this.designationRespo = designationRespo;
		this.jobDetailsRespo = jobDetailsRespo;
		this.personalDetailsRespo = personalDetailsRespo;
		this.bankDetailsRespo = bankDetailsRespo;
		this.employeeStatutoryDetailsRespo = employeeStatutoryDetailsRespo;
		this.salaryDetailsRespo = salaryDetailsRespo;
	}

	@Override
    public String createDepartment(DepartmentDto dto, HttpSession session) {

        Long loggedInUserId =
                (Long) session.getAttribute("LOGGED_IN_USER_ID");

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
		Long loggedInUserId=(Long)session.getAttribute("LOGGED_IN_USER_ID");
		if(loggedInUserId==null) {
			throw new RuntimeException("User not logged in");
		}
		
		User createdBy=userResp.findById(loggedInUserId)
				.orElseThrow(()->new RuntimeException("user not find"));
		if(designationRespo.existsByDesignationName(dto.getDesignationName())) {
			throw new RuntimeException("Designation is exists");
		
	}
		Designation designation=new Designation();
		designation.setCreatedBy(createdBy);
		designation.setDesignationName(dto.getDesignationName());
		designationRespo.save(designation);
		System.out.println(dto.getDesignationName());
		return "created successfull";
	}

	@Override
	@Transactional
	public String empOnBoarding(OnboardingRequestDTO dto) {
		
		User user=userResp.findById(dto.getUserId())
				.orElseThrow(()->new RuntimeException("user not found"));
		
		//personal Details
		PersonalDetails p=new PersonalDetails();
		p.setFirstName(dto.getPersonalDetailsDTO().getFirstName());
		p.setLastName(dto.getPersonalDetailsDTO().getLastName());
		p.setFullName(dto.getPersonalDetailsDTO().getFullName());
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
		System.out.println(p);
		//bank details
		BankDetails b=new BankDetails();
		b.setBankName(dto.getBankDetailsDTO().getBankName());
		b.setAccountNumber(dto.getBankDetailsDTO().getAccountNumber());
		b.setIfsc(dto.getBankDetailsDTO().getIfsc());
		b.setUser(user);
		bankDetailsRespo.save(b);
		System.out.println(b);
		//employee statutory Details
		
		EmployeeStatutoryDetails e=new EmployeeStatutoryDetails();
		e.setEsi(dto.getEmployeeStatutoryDetailsDTO().getEsi());
		e.setMin(dto.getEmployeeStatutoryDetailsDTO().getMin());
		e.setPfUan(dto.getEmployeeStatutoryDetailsDTO().getPfUan());
		e.setUser(user);
		employeeStatutoryDetailsRespo.save(e);
		System.out.println(e);
		// job details
		JobDetails j=new JobDetails();
		j.setDateOfJoining(dto.getJobDetailsDTO().getDateOfJoining());
		j.setUser(user);
		j.setWorkLocation(dto.getJobDetailsDTO().getWorkLocation());
		 
		Department department=departmentRespo.findById(dto.getJobDetailsDTO().getDepartmentId())
				.orElseThrow(()->new RuntimeException("Department not found"));
		
		Designation designation=designationRespo.findById(dto.getJobDetailsDTO().getDesignationId())
				.orElseThrow(()->new RuntimeException("Designation not found"));
		
		j.setDepartment(department);
		j.setDesignation(designation);
		jobDetailsRespo.save(j);
		
		System.out.println(j);
		//salary
		SalaryDetails s=new SalaryDetails();
		s.setBasic(dto.getSalaryDetailsDTO().getBasic());
		s.setConveyanceAllowance(dto.getSalaryDetailsDTO().getConveyanceAllowance());
		s.setCtc(dto.getSalaryDetailsDTO().getCtc());
		s.setUser(user);
		s.setHra(dto.getSalaryDetailsDTO().getHra());
		salaryDetailsRespo.save(s);
		System.out.println(s);
		return "OnBoarding successfull";
	}
}
