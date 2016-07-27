package com.evc.tasks.usertasks;


import android.os.AsyncTask;

import com.evc.models.Company;
import com.evc.models.User;
import com.evc.transport.NetworkEventListener;

import java.util.List;

public abstract class UserServiceUsersTask extends AsyncTask<String, Void, List<User>> {
    NetworkEventListener networkEventListener;

    public void setNetworkEventListener(NetworkEventListener networkEventListener) {
        this.networkEventListener = networkEventListener;
    }

}