package com.hutech.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hutech.admin.config.JwtService;
import com.hutech.admin.config.JwtUserDetailsService;
import com.hutech.admin.entity.Admin;
import com.hutech.admin.entity.JwtRequest;
import com.hutech.admin.entity.JwtResponse;
import com.hutech.admin.exceptionhandler.ResourceNotFoundException;
import com.hutech.admin.repository.AdminRepository;

import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private JavaMailSender mailSender;

	@PostMapping("/admin/singup")
	public ResponseEntity<?> responseEntity(@RequestBody JwtRequest jwtRequest) throws Exception {
		authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
		final String token = jwtService.dogenerateToken(userDetails);

		if (adminRepository.findByEmail(jwtRequest.getEmail()) == null) {
			throw new ResourceNotFoundException("given Emailid not present in Db", 410);
		}
		Admin admin = adminRepository.findByEmail(jwtRequest.getEmail()).get();
		String username = admin.getUsername();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		mimeMessageHelper.setTo(jwtRequest.getEmail());
		mimeMessageHelper.setSubject("Welcome to Admin Team");
		String content = ("Dear " + username + ",<br><br>Thank you for Login, have you on successfully logIn.<br>.<br>"
				+ "<br>This is a system-generated mail. Please do not reply to this mail.<br><br>Thanks,<br>admin.");
		mimeMessage.setContent(content, "text/html; charset=utf-8");
		mailSender.send(mimeMessage);

		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setJwtToken(token);
		jwtResponse.setAdminId(admin.getAdminId());
		return ResponseEntity.ok(jwtResponse);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}