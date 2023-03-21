package com.hutech.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hutech.admin.Iservice.AdminService;
import com.hutech.admin.dto.AdminRequest;
import com.hutech.admin.dto.CoureseDto;
import com.hutech.admin.entity.Users;
import com.hutech.admin.exceptionhandler.ResourceNotFoundException;
import com.hutech.admin.response.CoureseResponse;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/v1")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/admin/register")
	public String register(@RequestBody AdminRequest admin) {
		return adminService.createAdmin(admin);

	}
    @PostMapping("/admin/{adminId}/create/course")
    public  List<CoureseResponse> createCourse(@RequestBody List<CoureseDto> courseDtoList ,@PathVariable String adminId)
     throws ResourceNotFoundException, MessagingException{
    	return adminService.createCourses(courseDtoList,adminId);
    }
    
    @GetMapping("/admin/{adminId}")
    public List<CoureseResponse>findAllCourse(@PathVariable String adminId)
    throws ResourceNotFoundException{
    	return adminService.getAllcourses(adminId);
    }
    @PostMapping("/admin/{adminId}/course/{courseId}/customer")
    public Users create(@RequestBody Users users,@PathVariable String adminId, @PathVariable String courseId)throws ResourceNotFoundException, MessagingException {
    	return adminService.saveUser(users,adminId,courseId);
    }
}
