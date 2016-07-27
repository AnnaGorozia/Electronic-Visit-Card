package com.evc.tasks.usertasks;

import android.os.AsyncTask;

import com.evc.transport.NetworkEventListener;


public abstract class UserServiceTask extends AsyncTask<String, Void, String>{
	NetworkEventListener networkEventListener;

	public void setNetworkEventListener(NetworkEventListener networkEventListener) {
		this.networkEventListener = networkEventListener;
	}
	

}
