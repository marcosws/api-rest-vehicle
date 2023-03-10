package com.github.marcosws.vehicle.support.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.github.marcosws.vehicle.support.security.jwt.filter.AuthenticationFilter;
import com.github.marcosws.vehicle.support.security.jwt.filter.AuthorizationFilter;
import com.github.marcosws.vehicle.support.security.jwt.handler.AccessDeniedHandler;
import com.github.marcosws.vehicle.support.security.jwt.handler.UnauthorizedHandler;
import com.github.marcosws.vehicle.support.security.services.UserDetailsServiceImpl;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UnauthorizedHandler unauthorizedHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

	@Bean
	public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
		  
	      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	      provider.setPasswordEncoder(passwordEncoder);
	      provider.setUserDetailsService(userDetailsService);
	      return new ProviderManager(provider);
	      
	  }
	  
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
	  
	  @Bean 
	  public AuthenticationFilter authenticationFilter() {
		  return new AuthenticationFilter(authenticationManager(passwordEncoder(), userDetailsService));
	  }
	  
	  public AuthorizationFilter authorizationFilter() {
		  return new AuthorizationFilter(authenticationManager(passwordEncoder(), userDetailsService), userDetailsService);
	  }
	  
	  @Bean
	  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.cors().and().csrf().disable()
	        .exceptionHandling()
	        .authenticationEntryPoint(unauthorizedHandler).and()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	        .authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
	        .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
	        .anyRequest().authenticated();
	    
	    http.addFilter(authenticationFilter())
	    .addFilter(authorizationFilter()).exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	   
	    return http.build();
	    
	  }

}
