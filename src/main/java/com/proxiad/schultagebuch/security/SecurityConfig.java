package com.proxiad.schultagebuch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetails;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetails).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/benutzer", "/schuler", "/elternteil", "/lehrer", "/schulfach", "/schulstunde", "/klasse")
				.hasRole("ADMIN").antMatchers("/my-grades").hasAnyRole("SCHULER").antMatchers("/my-children")
				.hasRole("ELTERNTEIL").antMatchers("/home", "/home/*").authenticated().and().formLogin()
				.loginPage("/login").defaultSuccessUrl("/home", true).permitAll();

		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

	}

}
