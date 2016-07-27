package com.evc.tasks.historytasks;

import android.os.AsyncTask;

import com.evc.transport.NetworkEventListener;

public abstract class HistoryServiceTask extends AsyncTask<String, Void, String> {
    NetworkEventListener networkEventListener;

    public void setNetworkEventListener(NetworkEventListener networkEventListener) {
        this.networkEventListener = networkEventListener;
    }
}