package com.hutech.admin.dto;

import lombok.Data;

@Data
public class CoureseDto {
	

	private String adminId;
	
	private String courseName;
	
	private String description;
	
	
	
	private String trainerName;
	
	private String trainerEmail;
	
	private Integer durationInMounths;
}