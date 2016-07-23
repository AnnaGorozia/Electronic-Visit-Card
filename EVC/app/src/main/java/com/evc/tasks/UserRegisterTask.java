package com.evc.tasks;

import android.os.AsyncTask;

import com.evc.transport.NetworkEventListener;


public abstract class UserRegisterTask extends AsyncTask<String, Void, String>{
	private NetworkEventListener networkEventListener;

	public void setNetworkEventListener(NetworkEventListener networkEventListener) {
		this.networkEventListener = networkEventListener;
	}
	
	@Override
	protected void onPostExecute(String message) {
		super.onPostExecute(message);
		networkEventListener.onUserRegistered(message);
	}
}
