package com.hutech.admin.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hutech.admin.Iservice.AdminService;
import com.hutech.admin.dto.AdminDto;
import com.hutech.admin.dto.AdminRequest;
import com.hutech.admin.dto.CoureseDto;
import com.hutech.admin.entity.Admin;
import com.hutech.admin.entity.Course;
import com.hutech.admin.entity.Users;
import com.hutech.admin.exceptionhandler.ResourceNotFoundException;
import com.hutech.admin.repository.AdminRepository;
import com.hutech.admin.repository.CouresRepository;
import com.hutech.admin.repository.UserRepository;
import com.hutech.admin.response.CoureseResponse;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private CouresRepository couresRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepository userRepository;

	@Override
	public String createAdmin(AdminRequest admin) {
		Admin admin2 = mapper.map(admin, Admin.class);
		adminRepository.save(admin2);
		return "admin is saved";
	}

	@Override
	public String login(AdminDto admin) throws MessagingException, ResourceNotFoundException {

		if (adminRepository.findByEmail(admin.getEmail()) == null) {
			throw new ResourceNotFoundException("given Emailid not present in Db", 410);
		}
		String username = admin.getEmail();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		mimeMessageHelper.setTo(admin.getEmail());
		mimeMessageHelper.setSubject("Welcome to Admin Team");
		String content = ("Dear " + username + ",<br><br>Thank you for Login, have you on successfully logIn.<br>.<br>"
				+ "<br>This is a system-generated mail. Please do not reply to this mail.<br><br>Thanks,<br>admin.");
		mimeMessage.setContent(content, "text/html; charset=utf-8");
		mailSender.send(mimeMessage);
		return "Login Successfully and mail send to your c/s email";
	}

	@Override
	public List<CoureseResponse> createCourses(List<CoureseDto> courseDtoList, String adminId)
			throws ResourceNotFoundException, MessagingException {

		Admin admin = adminRepository.findByAdminId(adminId);
		if (admin == null) {
			throw new ResourceNotFoundException("Admin with given ID not found", 404);
		}

		List<CoureseResponse> responses = new ArrayList<>();
		for (CoureseDto courseDto : courseDtoList) {

			Course course = mapper.map(courseDto, Course.class);
			course.setAdminId(adminId);
			;

			String trainerEmail = course.getTrainerEmail();
			String trainerName = course.getTrainerName();
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			mimeMessageHelper.setTo(trainerEmail);
			mimeMessageHelper.setSubject("Welcome to the team, " + trainerName);
			String content = "Dear " + trainerName + ",<br><br>You have been assigned to the course \""
					+ course.getCourseName() + "\" as a trainer. You are expected to work until "
					+ course.getEndedDuration()
					+ ".<br>This is a system-generated email. Please do not reply to this email.";
			mimeMessageHelper.setText(content, true);
			mailSender.send(mimeMessage);

			Integer durationInMonths = course.getDurationInMounths();
			if (durationInMonths == 1) {
				course.setStartedDuration(LocalDate.now());
				course.setEndedDuration(LocalDate.now().plusDays(30));
			} else if (durationInMonths == 3) {
				course.setStartedDuration(LocalDate.now());
				course.setEndedDuration(LocalDate.now().plusDays(30 * 3));
			} else if (durationInMonths == 6) {
				course.setStartedDuration(LocalDate.now());
				course.setEndedDuration(LocalDate.now().plusDays(30 * 6));
			} else {
				course.setStartedDuration(LocalDate.now());
				course.setEndedDuration(LocalDate.now().plusDays(365));
			}

			Course savedCourse = couresRepository.save(course);

			if (!couresRepository.existsByTrainerEmail(trainerEmail)) {
				throw new ResourceNotFoundException("Trainer with email " + trainerEmail + " not found in database",
						404);
			}

			CoureseResponse response = mapper.map(savedCourse, CoureseResponse.class);
			responses.add(response);
		}
		return responses;
	}

	@Override
	public List<CoureseResponse> getAllcourses(String adminId) throws ResourceNotFoundException {
		if (adminRepository.findByAdminId(adminId) == null) {
			throw new ResourceNotFoundException("Given adminId not present in Db", 410);
		}

		List<Course> courses = couresRepository.findByAdminId(adminId);
		List<CoureseResponse> responses = new ArrayList<>();

		for (Course course : courses) {
			CoureseResponse response = mapper.map(course, CoureseResponse.class);
			responses.add(response);
		}

		return responses;
	}

	public Users saveUser(Users users, String adminId, String courseId) throws ResourceNotFoundException, MessagingException {
	    if (adminRepository.findByAdminId(adminId) == null) {
	        throw new ResourceNotFoundException("Given adminId not present in Db", 410);
	    }
	    Course course = couresRepository.findByCourseId(courseId);
	    if (course == null) {
	        throw new ResourceNotFoundException("Given courseId not present in Db", 410);
	    }
	    String username = users.getUserName();
	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
	    mimeMessageHelper.setTo(users.getEmail());
	    mimeMessageHelper.setSubject("Welcome to " + course.getCourseName());
	    String content = "Dear " + username + ",<br><br>"
	            + "Welcome to " + course.getCourseName() + " course. We are excited to have you on board.<br>"
	            + "Your login credentials are:<br>"
	            + "Username: " + users.getUserName() + "<br>"
	            
	            + "Please log in to the system using the above credentials and start your learning journey.<br><br>"
	            + "Best regards,<br>"
	            + "Team " + course.getCourseName();
	    mimeMessage.setContent(content, "text/html; charset=utf-8");
	    mailSender.send(mimeMessage);
	    return userRepository.save(users);
	}
}	





