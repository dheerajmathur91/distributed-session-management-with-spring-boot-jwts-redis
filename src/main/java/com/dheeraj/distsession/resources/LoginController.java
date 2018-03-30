package com.dheeraj.distsession.resources;

import com.dheeraj.distsession.constants.AppConstants;
import com.dheeraj.distsession.constants.MessageConstants;
import com.dheeraj.distsession.request.LoginRequest;
import com.dheeraj.distsession.response.GenericResponse;
import com.dheeraj.distsession.response.LoginResponse;
import com.dheeraj.distsession.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class LoginController {
    private static final Map<String, String> credentials = new HashMap<>();
    
    @Autowired
    private JwtUtil jwtUtil;

    public LoginController() {
        credentials.put("dheeraj", "mathur");
    }
    
    /**
     * Login call, does following work:
     * - Validates credentials
     * - Generates new token
     * This call does not need token authentication. Hence, it is excluded in @JwtInterceptor
     * @param loginRequest Request contains user credentials
     * @return
     */
    @RequestMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
    	LoginResponse loginResponse = new LoginResponse();
    	String username = loginRequest.getUsername();
    	String password = loginRequest.getPassword();
    	
    	// validates user credentials here
    	if (username == null || !credentials.containsKey(username) || !credentials.get(username).equals(password)){
          loginResponse.setException(true);
          loginResponse.addMessage(MessageConstants.INVALID_USERNAME_PASSWORD);
          return loginResponse;
      }
    	
      String token = jwtUtil.generateToken(username);
      loginResponse.setToken(token);
      loginResponse.addMessage(MessageConstants.LOGIN_SUCCESS);
      return loginResponse;
    }
    
    /**
     * Sample resource which needs authentication to be executed. 
     * Authentication is provided through @JwtInterceptor
     * @return
     */
    @RequestMapping("/logintest")
    public GenericResponse logintest(){
    	GenericResponse genericResponse = new GenericResponse();
    	genericResponse.addMessage(MessageConstants.AUTHENTICATION_SUCCESSFUL);
    	return genericResponse;
    }
    
    /**
     * Invalidates user token to logout. 
     * Also, check the validity of the token again before sending final response.
     * @param authToken Authentication token
     * @return
     */
    @RequestMapping("/logout")
    public GenericResponse logout(@RequestHeader(AppConstants.AUTHORIZATION) String authToken){
    	GenericResponse genericResponse = new GenericResponse();
    	jwtUtil.invalidateToken(authToken);
        genericResponse.addMessage(MessageConstants.LOGGED_OUT_SUCCESSFULLY);
    	return genericResponse;
    }
}