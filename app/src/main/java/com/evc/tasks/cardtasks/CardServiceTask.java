package com.evc.tasks.cardtasks;

import android.os.AsyncTask;

import com.evc.transport.NetworkEventListener;

public abstract class CardServiceTask extends AsyncTask<String, Void, String> {
    NetworkEventListener networkEventListener;

    public void setNetworkEventListener(NetworkEventListener networkEventListener) {
        this.networkEventListener = networkEventListener;
    }


}