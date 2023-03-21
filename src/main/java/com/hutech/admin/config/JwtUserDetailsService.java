package com.hutech.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hutech.admin.entity.Admin;
import com.hutech.admin.repository.AdminRepository;



@Service
public class JwtUserDetailsService implements UserDetailsService {

		@Autowired
		private AdminRepository adminRepository;

		@Override
		public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
			Admin user = adminRepository.findByEmail(email).orElseThrow( () -> new UsernameNotFoundException("user not found"));
			CustomUserDetails userDetails = new CustomUserDetails();
			userDetails.setUser(user);
			return userDetails;
		}

	}


