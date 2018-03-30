package com.dheeraj.distsession.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dheeraj.distsession.constants.AppConstants;
import com.dheeraj.distsession.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

	@Autowired
    private JwtUtil jwtUtil;

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		
		// Create a request wrapper to work on headers
		HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(req);
		String authToken = requestWrapper.getHeader(AppConstants.AUTHORIZATION);
		
		// If token is invalid, mark request as Unauthorized
		if(authToken == null || jwtUtil.validateToken(authToken) == null){
			((HttpServletResponse) response)
			.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		} 
		return super.preHandle(request, response, handler);
	}
	
}
