package com.hutech.admin.response;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class CoureseResponse {

	private Long id;

	private String adminId;
	
	private String courseId;
	
	private String courseName;

	private String description;

	private String trainerName;

	private LocalDate startedDuration;

	private LocalDate endedDuration;

}
