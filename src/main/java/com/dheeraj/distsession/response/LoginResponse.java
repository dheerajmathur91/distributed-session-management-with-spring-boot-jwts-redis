package com.dheeraj.distsession.response;

public class LoginResponse extends AbstractBaseResponse {
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
