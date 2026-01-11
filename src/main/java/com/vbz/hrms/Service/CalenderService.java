package com.vbz.hrms.Service;

import java.util.List;

import com.vbz.hrms.model.Calender;

public interface CalenderService {

	String creatingDate(Calender calender);

	String deleteCalender(Long id);

	List<Calender> getlist();

	String editClaender(Calender calender, Long id);

}
