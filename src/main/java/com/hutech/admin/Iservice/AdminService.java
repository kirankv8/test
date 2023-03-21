package com.hutech.admin.Iservice;

import java.util.List;

import com.hutech.admin.dto.AdminDto;
import com.hutech.admin.dto.AdminRequest;
import com.hutech.admin.dto.CoureseDto;
import com.hutech.admin.entity.Admin;
import com.hutech.admin.entity.Users;
import com.hutech.admin.exceptionhandler.ResourceNotFoundException;

import jakarta.mail.MessagingException;
import com.hutech.admin.response.CoureseResponse;
public interface AdminService  {

	 public String createAdmin(AdminRequest admin);

	public String login(AdminDto admin) throws MessagingException, ResourceNotFoundException;

	public List<CoureseResponse> getAllcourses(String adminId)throws ResourceNotFoundException;

	public Users saveUser(Users users, String adminId, String courseId
			) throws ResourceNotFoundException, MessagingException;

	List<CoureseResponse> createCourses(List<CoureseDto> courseDtoList, String adminId)
			throws ResourceNotFoundException, MessagingException;

	
}
