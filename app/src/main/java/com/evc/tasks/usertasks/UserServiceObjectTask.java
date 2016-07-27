package com.evc.tasks.usertasks;

import android.os.AsyncTask;

import com.evc.models.User;
import com.evc.transport.NetworkEventListener;

public abstract class UserServiceObjectTask extends AsyncTask<String, Void, User> {
    NetworkEventListener networkEventListener;

    public void setNetworkEventListener(NetworkEventListener networkEventListener) {
        this.networkEventListener = networkEventListener;
    }

}