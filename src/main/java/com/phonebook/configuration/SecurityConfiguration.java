package com.phonebook.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.phonebook.service.impl.AccountDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

	@Autowired 
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AccountDetailsServiceImpl accountDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		  http.csrf().disable().authorizeRequests()
		  .antMatchers("/webjars/**").permitAll()
		  .antMatchers("/css/**").permitAll()
		  .antMatchers("/").permitAll()
		  .antMatchers("/registration").permitAll()
		  .antMatchers("/authorization").anonymous()
		  		.anyRequest().authenticated()	
		  	.and()
		  		.formLogin().loginPage("/login").permitAll()
		        .defaultSuccessUrl("/phonebook",true).failureUrl("/authorization?error=true")
		  	.and().logout().permitAll().logoutSuccessUrl("/authorization");
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountDetailsService);
	}
}









