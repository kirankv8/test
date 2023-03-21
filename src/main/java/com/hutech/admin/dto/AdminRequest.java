package com.hutech.admin.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class AdminRequest {
	private String username;

	private String email;

	private String password;

	private String phonenumber;

}
