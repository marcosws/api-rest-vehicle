package com.github.marcosws.vehicle.support.security.jwt.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.github.marcosws.vehicle.api.user.RoleEntity;
import com.github.marcosws.vehicle.api.user.UserDTO;
import com.github.marcosws.vehicle.api.user.UserEntity;
import com.github.marcosws.vehicle.support.security.jwt.utils.JwtUtil;
import com.github.marcosws.vehicle.support.security.jwt.utils.ServletUtil;
import com.github.marcosws.vehicle.support.security.services.UserDetailsImpl;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final String AUTH_URL = "/api/v1/login";

    private AuthenticationManager authenticationManager;
    
    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
    	
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(AUTH_URL);
        
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {
        	
            LoginInput login = new ObjectMapper().readValue(request.getInputStream(), LoginInput.class);
            String username = login.getUsername();
            String password = login.getPassword();

            if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) 
                throw new BadCredentialsException("Usuário e(ou) Senha Inválidos!");
            
            Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(auth);
            
        } 
        catch (IOException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException {
    	
    	UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
    	
    	System.out.println("DEBUG: Id       >>>> " + userDetailsImpl.getId());
        System.out.println("DEBUG: Username >>>> " + userDetailsImpl.getUsername());
        System.out.println("DEBUG: Password >>>> " + userDetailsImpl.getPassword());
        System.out.println("DEBUG: Password >>>> " + userDetailsImpl.getAuthorities().toString());
        
        UserEntity user = new UserEntity();
        user.setId(userDetailsImpl.getId());
        user.setUsername(userDetailsImpl.getUsername());
        user.setPassword(userDetailsImpl.getPassword());
        user.setRoles(getRolesFromCollection(userDetailsImpl.getAuthorities()));
        user.getRoles().forEach((r) ->{
        	System.out.println("DEBUG: >>>>> user.getRoles(): "  + r.getName());
        });
        
      
        String jwtToken = JwtUtil.createToken(userDetailsImpl);
        String json = UserDTO.create(user, jwtToken).toJson();
        ServletUtil.write(response, HttpStatus.OK, json);
        
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException error) throws IOException, ServletException {

        String json = ServletUtil.getJson("error", "Login incorreto");
        ServletUtil.write(response, HttpStatus.UNAUTHORIZED, json);
        
    }
    
    private List<RoleEntity> getRolesFromCollection(Collection<? extends GrantedAuthority> authorities){
    	
    	String stringRoles = authorities.toString().replace("[", "").replace("]", "");
    	List<String> listRoles = Arrays.asList(stringRoles.split(","));
    	List<RoleEntity> roles = new ArrayList<RoleEntity>();
    	listRoles.forEach((r) ->{
    		RoleEntity role = new RoleEntity();
    		role.setName(r.trim());
    		System.out.println("DEBUG: >>> " + role.getName());
    		roles.add(role);
    	});
    	
		return roles;
    	
    }


}
