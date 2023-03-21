package com.hutech.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hutech.admin.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

	Optional<Admin> findByEmail(String email) ;
	
	Admin findByAdminId(String adminId);
	
	}

	
