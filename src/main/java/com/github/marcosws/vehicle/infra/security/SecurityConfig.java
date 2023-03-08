package com.github.marcosws.vehicle.infra.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.github.marcosws.vehicle.infra.security.jwt.JwtAuthenticationFilter;
import com.github.marcosws.vehicle.infra.security.jwt.JwtAuthorizationFilter;
import com.github.marcosws.vehicle.infra.security.jwt.handler.AccessDeniedHandler;
import com.github.marcosws.vehicle.infra.security.jwt.handler.UnauthorizedHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
    @Autowired
    private UnauthorizedHandler unauthorizedHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

	/*@Bean                                                             
	public UserDetailsService userDetailsService() throws Exception {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("SENHA USER: " + encoder.encode("user"));
		System.out.println("SENHA ADMIN: " + encoder.encode("admin"));
		
		// ensure the passwords are encoded properly
		UserBuilder users = User.withDefaultPasswordEncoder();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(users.username("user").password("user").roles("USER").build());
		manager.createUser(users.username("admin").password("admin").roles("USER","ADMIN").build());
		return manager;
	} */
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
          .userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder())
          .and()
          .build();
    }
    

	@Bean
	@Order(1)                                                        
	public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/api/v1/admin**")                                   
			.authorizeHttpRequests(authorize -> authorize
				.anyRequest().hasRole("ADMIN")
			)
			.httpBasic(withDefaults());
		return http.build();
	} 

	@Bean
	public PasswordEncoder passwordEncoder() {
	     return new BCryptPasswordEncoder();    
	}
	
	@Bean                                                            
	public SecurityFilterChain formFilterChain(HttpSecurity http) throws Exception {
		
       /* AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build(); */
		
        AuthenticationManager authenticationManager = authenticationManager(http) ;
		
		http
			.authorizeHttpRequests(auth -> {
				try {
					
					auth
					.requestMatchers(HttpMethod.GET, "/api/v1/login").permitAll()
					.requestMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
					.anyRequest()
					.authenticated()
					.and().csrf().disable()
					.addFilter(new JwtAuthenticationFilter(authenticationManager))
					.addFilter(new JwtAuthorizationFilter(authenticationManager, userDetailsService))
					.exceptionHandling()
			        .accessDeniedHandler(accessDeniedHandler)
			        .authenticationEntryPoint(unauthorizedHandler)
			        .and()
			        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				/*	auth	
						.anyRequest()
						.authenticated()
						.and()
						.authenticationManager(authenticationManager) 
						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); */
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			)
			.httpBasic(withDefaults());  /* .formLogin(withDefaults()) */
		return http.build();
	}
	/*
	private static final String[] AUTH_WHITELIST = {
	
	// for Swagger UI v2
	"/v2/api-docs",
	"/swagger-ui.html",
	"/swagger-resources",
	"/swagger-resources/**",
	 "/configuration/ui",
	 "/configuration/security",
	"/webjars/**",
	
	 // for Swagger UI v3 (OpenAPI)
	"/v3/api-docs/**",
	 "/swagger-ui/**"
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		    	
		http.authorizeHttpRequests((auth) -> auth
		.requestMatchers(AUTH_WHITELIST).permitAll());
		return http.build();
	   
	} */


}
