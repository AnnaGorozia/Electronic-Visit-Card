package com.evc.transport;

import com.evc.models.User;

public interface NetworkEventListener {
	public void onUserRegistered(String message);
	public void onUserLoggedIn(String message);
	public void onUserObjectReturned(User user);
    public void onUserObjectByMail(User user);

}
