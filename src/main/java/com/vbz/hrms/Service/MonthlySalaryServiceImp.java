package com.vbz.hrms.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vbz.hrms.Respositoy.MonthlySalaryRespo;
import com.vbz.hrms.Respositoy.SalaryDetailsRespo;
import com.vbz.hrms.Respositoy.UserResp;
import com.vbz.hrms.dto.MonthlySalaryDto;
import com.vbz.hrms.dto.MonthlySalaryResponseDto;
import com.vbz.hrms.model.MonthlySalary;
import com.vbz.hrms.model.SalaryDetails;
import com.vbz.hrms.model.User;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class MonthlySalaryServiceImp implements MonthlySalaryService {

    private final UserResp userResp;
    private final SalaryDetailsRespo salaryDetailsRespo;
    private final MonthlySalaryRespo monthlySalaryRespo;

    public MonthlySalaryServiceImp(
            UserResp userResp,
            SalaryDetailsRespo salaryDetailsRespo,
            MonthlySalaryRespo monthlySalaryRespo) {
        this.userResp = userResp;
        this.salaryDetailsRespo = salaryDetailsRespo;
        this.monthlySalaryRespo = monthlySalaryRespo;
    }

    @Override
    @Transactional
    public String generateMonthlySalary(MonthlySalaryDto dto, HttpSession session) {

        Long hrId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
        if (hrId == null) {
            throw new IllegalStateException("HR not logged in");
        }

        User generatedBy = userResp.findById(hrId)
                .orElseThrow(() -> new EntityNotFoundException("HR not found"));

     
        if (dto.getActualDay() > dto.getTotalDay()) {
            throw new IllegalArgumentException(
                    "Actual working days cannot exceed total working days");
        }


        User employee = userResp.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        SalaryDetails salaryDetails = salaryDetailsRespo.findByUser(employee)
                .orElseThrow(() -> new EntityNotFoundException("Salary details not found"));


        BigDecimal months = BigDecimal.valueOf(12);

        BigDecimal basicPerMonth =
                salaryDetails.getBasic().divide(months, 2, RoundingMode.HALF_UP);

        BigDecimal hraPerMonth =
                salaryDetails.getHra().divide(months, 2, RoundingMode.HALF_UP);

        BigDecimal conveyancePerMonth =
                salaryDetails.getConveyanceAllowance().divide(months, 2, RoundingMode.HALF_UP);

        BigDecimal totalMonthlySalary =
                basicPerMonth.add(hraPerMonth).add(conveyancePerMonth);

      
        BigDecimal totalDaysBD = BigDecimal.valueOf(dto.getTotalDay());
        BigDecimal actualDaysBD = BigDecimal.valueOf(dto.getActualDay());

        BigDecimal perDaySalary =
                totalMonthlySalary.divide(totalDaysBD, 2, RoundingMode.HALF_UP);

        boolean alreadyGenerated =
                monthlySalaryRespo.existsByUserAndMonthAndYear(
                        employee,
                        dto.getMonth(),
                        dto.getYear()
                );

        if (alreadyGenerated) {
            throw new IllegalStateException(
                    "Salary already generated for this employee for "
                            + dto.getMonth() + "/" + dto.getYear()
            );
        }
        BigDecimal basicPay =
                basicPerMonth.divide(totalDaysBD, 2, RoundingMode.HALF_UP)
                        .multiply(actualDaysBD);

        BigDecimal hraPay =
                hraPerMonth.divide(totalDaysBD, 2, RoundingMode.HALF_UP)
                        .multiply(actualDaysBD);

        BigDecimal conveyancePay =
                conveyancePerMonth.divide(totalDaysBD, 2, RoundingMode.HALF_UP)
                        .multiply(actualDaysBD);

        BigDecimal actualGrossSalary =
                basicPay.add(hraPay).add(conveyancePay)
                        .setScale(2, RoundingMode.HALF_UP);

        BigDecimal expectedGrossSalary =
                perDaySalary.multiply(actualDaysBD)
                        .setScale(2, RoundingMode.HALF_UP);

        BigDecimal lop =
                expectedGrossSalary.subtract(actualGrossSalary)
                        .setScale(2, RoundingMode.HALF_UP);

        MonthlySalary monthlySalary = new MonthlySalary();
        monthlySalary.setUser(employee);
        monthlySalary.setGeneratedBy(generatedBy);
        monthlySalary.setMonth(dto.getMonth());
        monthlySalary.setYear(dto.getYear());
        monthlySalary.setTotalDay(dto.getTotalDay());
        monthlySalary.setActualDay(dto.getActualDay());
        monthlySalary.setBasic(basicPay);
        monthlySalary.setHra(hraPay);
        monthlySalary.setConveyanceAllowance(conveyancePay);
        monthlySalary.setGrossSalary(actualGrossSalary);
        monthlySalary.setLop(lop);

        monthlySalaryRespo.save(monthlySalary);

        return "Monthly salary generated successfully";
    }

    @Override
    public List<MonthlySalaryResponseDto> getsalaryListMonthAndYear(
            Integer year, Integer month) {

        List<MonthlySalary> list =
                monthlySalaryRespo.findByMonthAndYear(month, year);

        if (list.isEmpty()) {
            throw new IllegalStateException(
                    "No salary records found for " + month + "/" + year);
        }

        return list.stream().map(s -> {
            MonthlySalaryResponseDto dto = new MonthlySalaryResponseDto();

            dto.setSalaryId(s.getId());
            dto.setEmployeeId(s.getUser().getId());
           
            dto.setEmployeeName(s.getUser().getUsername()); 
            dto.setTotalDay(s.getTotalDay());
            dto.setActualDay(s.getActualDay());
            dto.setBasic(s.getBasic());
            dto.setHra(s.getHra());
            dto.setConveyanceAllowance(s.getConveyanceAllowance());
            dto.setGrossSalary(s.getGrossSalary());
            dto.setLop(s.getLop());
            dto.setMonth(s.getMonth());
            dto.setYear(s.getYear());

            return dto;
        }).toList();
    }

//	@Override
//	public List<MonthlySalary> employeeSalary(HttpSession session) {
//	    Long hrId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
//	    if(hrId==null) {
//	    	throw new IllegalStateException("user not login");
//	    }
//	    User user=userResp.findById(hrId)
//	    		.orElseThrow(()-> new EntityNotFoundException("user not found"));
//	    List<MonthlySalary>list=monthlySalaryRespo.findByUser(user);
//	    		
//		return list;
//	}


}
