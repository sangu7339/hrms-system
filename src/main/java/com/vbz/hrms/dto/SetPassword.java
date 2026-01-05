package com.vbz.hrms.dto;

import lombok.Data;

@Data
public class SetPassword {
	
	private String oldpassword;
	private String password;

}
