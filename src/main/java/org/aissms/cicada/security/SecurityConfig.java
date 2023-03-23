package org.aissms.cicada.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// don't create an AuthenticationManager bean
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired JwtRequestFilter jwtRequestFilter;
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(req->{
			req
			.antMatchers("/register", "/login", "/home", "/js/*", "/files/*")
			.permitAll()
			.anyRequest()
			.authenticated();
		})
		.csrf(cs -> {
			cs.disable();
		})
		.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
		.sessionManagement(session -> {
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		})
		.cors();
		return http.build();
	}
	// use while storing passwords in db
	@Bean
	public PasswordEncoder configPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}