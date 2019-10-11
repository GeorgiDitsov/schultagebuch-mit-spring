package com.proxiad.schultagebuch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
		http.authorizeRequests().antMatchers("/home", "/info").authenticated()
				.antMatchers("/benutzer", "/schuler", "/elternteil", "/lehrer", "/schulfach", "/schulstunde", "/klasse",
						"/semester")
				.hasRole("ADMIN").antMatchers("/meine-noten").hasAnyRole("SCHULER").antMatchers("/meine-kinder")
				.hasRole("ELTERNTEIL").antMatchers("/meine-schulstunden").hasRole("LEHRER").anyRequest().permitAll()
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/home", true).permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll().and().exceptionHandling()
				.accessDeniedPage("/unauthorized");
	}

}
