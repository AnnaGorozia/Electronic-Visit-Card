package com.evc.tasks.companytasks;

import android.os.AsyncTask;

import com.evc.models.Company;
import com.evc.transport.NetworkEventListener;

import java.util.List;

public abstract class UserServiceCompaniesTask extends AsyncTask<String, Void, List<Company>> {
    NetworkEventListener networkEventListener;

    public void setNetworkEventListener(NetworkEventListener networkEventListener) {
        this.networkEventListener = networkEventListener;
    }

}