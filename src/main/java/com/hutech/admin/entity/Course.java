package com.hutech.admin.entity;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String courseName;

	private String description;

	private String courseId;
	
	private String adminId;


	private String trainerName;
	

	private String trainerEmail;

	private LocalDate startedDuration;

	private LocalDate endedDuration;
	
	private Integer DurationInMounths;

	@PostPersist
	public void setCourseId() {
		if (id != null)
			courseId = "CUR" + 00 + id;
	}

}
