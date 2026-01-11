package com.vbz.hrms.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vbz.hrms.Respositoy.CalenderRespo;
import com.vbz.hrms.model.Calender;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CalenderServiceImp implements CalenderService {
	
	private final CalenderRespo calenderRespo;
	
	public CalenderServiceImp(CalenderRespo calenderRespo) {
		this.calenderRespo =calenderRespo;
	}

	@Override
	public String creatingDate(Calender calender) {
		calenderRespo.save(calender);
		return "creaated successfully";
		
	}

	@Override
	public String deleteCalender( Long id) {
		Calender calender2 =calenderRespo.findById(id)
				.orElseThrow(()-> new EntityNotFoundException("delete date not found"));
		calenderRespo.delete(calender2);
		return "Delete Successfully";
	}

	@Override
	public List<Calender> getlist() {
		List<Calender>list=calenderRespo.findAll();
		if(list.isEmpty()) {
			throw new NullPointerException("No Data");
		}
		return list;
	}

	@Override
	public String editClaender(Calender calender, Long id) {
		Calender calender2=calenderRespo.findById(id)
				.orElseThrow(()->new EntityNotFoundException("not found"));
		
		
				calender2.setDate(calender.getDate());
				calender2.setTitle(calender.getTitle());
				calender2.setType(calender.getType());
				calenderRespo.save(calender2);
		
		return "Update Successfully";
	}

}
