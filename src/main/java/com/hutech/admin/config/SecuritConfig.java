package com.hutech.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecuritConfig {
	
	@Autowired
	private JwtFilter jwtauthFilter;
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	
	

	@Bean
	public SecurityFilterChain securityFilterChain( HttpSecurity  http) throws Exception{	
		http.csrf().disable().authorizeHttpRequests()
			.requestMatchers("/api/v1/admin/register","/api/v1/admin/singup")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authenticationProvider(authenticationProvider)
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
				.addFilterBefore(jwtauthFilter, UsernamePasswordAuthenticationFilter.class);
				return http.build();
	}

}
