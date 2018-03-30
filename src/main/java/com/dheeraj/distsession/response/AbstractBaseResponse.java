package com.dheeraj.distsession.response;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBaseResponse {
	protected boolean exception = false;
	protected List<String> messages = new ArrayList<String>();
	
	public boolean isException() {
		return exception;
	}
	
	public void setException(boolean exception) {
		this.exception = exception;
	}
	
	public List<String> getMessages() {
		return messages;
	}
	
	public AbstractBaseResponse addMessages(List<String> messages) {
		this.messages = messages;
		return this;
	}
	
	public AbstractBaseResponse addMessage(String message) {
		this.messages.add(message);
		return this;
	}
}