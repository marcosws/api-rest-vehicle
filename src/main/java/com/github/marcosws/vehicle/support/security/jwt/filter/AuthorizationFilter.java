package com.github.marcosws.vehicle.support.security.jwt.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.github.marcosws.vehicle.support.security.jwt.utils.JwtUtil;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

    private UserDetailsService userDetailsService;
	
	
	public AuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}
	
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        if (StringUtils.isEmpty(token) || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {

            if(!JwtUtil.isTokenValid(token)) 
                throw new AccessDeniedException("Acesso negado.");
            
            String login = JwtUtil.getLogin(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);
            List<GrantedAuthority> authorities = JwtUtil.getRoles(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);

        } 
        catch (RuntimeException ex) {
        	
            logger.error("Authentication error: " + ex.getMessage(),ex);
            throw ex;
        }
    }
	

}
