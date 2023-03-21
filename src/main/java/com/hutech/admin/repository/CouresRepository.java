package com.hutech.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hutech.admin.entity.Course;

public interface CouresRepository extends JpaRepository<Course, Long>  {

	List<Course> findByAdminId(String adminId);
	
   Course findByTrainerEmail(String trainerEmail);
   
   Course findByCourseId(String courseId);

boolean existsByTrainerEmail(String trainerEmail);

}
