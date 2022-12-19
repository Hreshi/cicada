package org.aissms.cicada.configuration;


import org.aissms.cicada.events.LoginListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired LoginListener loginListener;
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.authorizeRequests(req->{
			req
			.antMatchers("/auth", "/logout")
			.permitAll()
			.anyRequest()
			.authenticated();
		})
		.oauth2Login(oauth -> {
			oauth
			.loginPage("/auth")
			.defaultSuccessUrl("/")
			.successHandler(loginListener);
		})
		.csrf(cs -> {
			cs
			.ignoringAntMatchers("/logout");
		})
		.logout(log -> {
			log
			.logoutUrl("/logout")
			.invalidateHttpSession(true)
			.logoutSuccessUrl("/auth");
		});
		return http.build();
	}
}