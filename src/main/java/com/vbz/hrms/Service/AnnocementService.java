package com.vbz.hrms.Service;

import java.util.List;

import com.vbz.hrms.model.Annocement;

import jakarta.servlet.http.HttpSession;

public interface AnnocementService {

	String annocementcreate(Annocement annocement, HttpSession session);

	String edit(Annocement annocement, HttpSession session, Long id);

	List<Annocement> getAll();
	

}
