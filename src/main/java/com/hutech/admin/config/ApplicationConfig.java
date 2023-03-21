
  package com.hutech.admin.config;
  
  import org.springframework.beans.factory.annotation.Autowired; import
  org.springframework.context.annotation.Bean; import
  org.springframework.context.annotation.Configuration; import
  org.springframework.security.authentication.AuthenticationManager; import
  org.springframework.security.authentication.AuthenticationProvider; import
  org.springframework.security.authentication.dao.DaoAuthenticationProvider;
  import
  org.springframework.security.config.annotation.authentication.configuration.
  AuthenticationConfiguration; import
  org.springframework.security.crypto.password.NoOpPasswordEncoder; import
  org.springframework.security.crypto.password.PasswordEncoder;
  
  @Configuration public class ApplicationConfig {
  
  @Autowired 
  private JwtUserDetailsService jwtUserDetailsService;
  
  @Bean public AuthenticationProvider authenticationProvider() {
  DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
  authProvider.setUserDetailsService(jwtUserDetailsService);
  authProvider.setPasswordEncoder(passwordEncoder()); return authProvider; }
  
  @Bean public AuthenticationManager
  authenticationManager(AuthenticationConfiguration config) throws Exception {
  return config.getAuthenticationManager(); }
  
  @Bean public PasswordEncoder passwordEncoder() { return
  NoOpPasswordEncoder.getInstance(); }
  
  }
 