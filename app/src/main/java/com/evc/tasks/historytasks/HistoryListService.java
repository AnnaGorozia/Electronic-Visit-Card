package com.evc.tasks.historytasks;

import android.os.AsyncTask;

import com.evc.models.HistoryEntry;
import com.evc.transport.NetworkEventListener;

import java.util.List;

public abstract class HistoryListService extends AsyncTask<String, Void, List<HistoryEntry>> {
    NetworkEventListener networkEventListener;

    public void setNetworkEventListener(NetworkEventListener networkEventListener) {
        this.networkEventListener = networkEventListener;
    }

}