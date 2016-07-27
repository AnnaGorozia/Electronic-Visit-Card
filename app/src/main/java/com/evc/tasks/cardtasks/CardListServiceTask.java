package com.evc.tasks.cardtasks;

import android.os.AsyncTask;

import com.evc.models.Card;
import com.evc.transport.NetworkEventListener;

import java.util.List;

public abstract class CardListServiceTask extends AsyncTask<String, Void, List<Card>> {
        NetworkEventListener networkEventListener;

public void setNetworkEventListener(NetworkEventListener networkEventListener) {
        this.networkEventListener = networkEventListener;
        }

        }