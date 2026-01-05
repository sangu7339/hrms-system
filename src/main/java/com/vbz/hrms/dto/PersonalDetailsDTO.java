package com.vbz.hrms.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class PersonalDetailsDTO {

    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String fullName;
    private LocalDate dob;

    private String nationality;
    private String maritalStatus;
    private String bloodGroup;

    private String aadhaarNumber;
    private String panNumber;

    private String phoneNumber;
    private String emailId;

    private String address1;
    private String address2;

    private String emergencyContactName;
    private String emergencyPhoneNumber;

   
}
