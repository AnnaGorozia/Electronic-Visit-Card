package com.evc.tasks;


import android.os.AsyncTask;

import com.evc.transport.NetworkEventListener;


public abstract class CompanyServiceTask extends AsyncTask<String, Void, String> {
    NetworkEventListener networkEventListener;

    public void setNetworkEventListener(NetworkEventListener networkEventListener) {
        this.networkEventListener = networkEventListener;
    }
}