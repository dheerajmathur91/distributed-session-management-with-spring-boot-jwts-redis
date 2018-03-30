package com.dheeraj.distsession.configuration;

import com.dheeraj.distsession.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.dheeraj.distsession.interceptor.JwtInterceptor;


@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Autowired
	private RedisConfiguration redisConfiguration;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).excludePathPatterns(AppConstants.LOGIN_URL);;
	}

}
