package com.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.management.userdetails.UserDetailsServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll()
				.antMatchers("/swagger-ui.html", "/employees/findAll", "/employees/findById",
						"/employees/findByFirstNameContainsAllIgnoreCase", "/employees/sortByFirstNameDirection",
						"/users/findAll", "/users/findById", "/roles/findAll", "/roles/findById")
				.hasAnyAuthority("USER", "ADMIN")
				.antMatchers("/employees/save", "/employees/deleteById", "/employees/updateById", "/users/save",
						"/users/deleteById", "/users/updateById", "/roles/save", "/roles/deleteById",
						"/roles/updateById")
				.hasAuthority("ADMIN").anyRequest().authenticated().and().formLogin().defaultSuccessUrl("/")
				.loginProcessingUrl("/login").permitAll().and().logout().logoutSuccessUrl("/").permitAll().and().cors()
				.and().csrf().disable();
	}

}
