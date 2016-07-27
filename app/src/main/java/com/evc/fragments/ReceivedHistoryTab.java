package com.evc.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.evc.MainActivity;
import com.evc.R;
import com.evc.adapters.SentCardsAdapter;
import com.evc.models.Card;
import com.evc.models.Company;
import com.evc.models.HistoryEntry;
import com.evc.models.User;
import com.evc.tasks.historytasks.HistoryListService;
import com.evc.tasks.historytasks.ServerReceivedHistoryDownloaded;
import com.evc.transport.NetworkEventListener;

import java.util.ArrayList;
import java.util.List;


public class ReceivedHistoryTab extends Fragment implements NetworkEventListener{

    private ListView listView;

    private SentCardsAdapter adapter;

    private  ArrayList<HistoryEntry> list;

    private ProgressDialog progressDialog;

    public static ReceivedHistoryTab newInstance() {
        return new ReceivedHistoryTab();
    }

    public ReceivedHistoryTab() {

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        list = new ArrayList<HistoryEntry>();

        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Refreshing History...");

        String[] taskParams = {MainActivity.getUser().getid()};

        HistoryListService historyListService = new ServerReceivedHistoryDownloaded();
        historyListService.setNetworkEventListener(this);
        historyListService.execute(taskParams);
        progressDialog.show();
        drawList();


    }

    private void drawList() {
        adapter = new SentCardsAdapter(getActivity(), list, "From");

        listView = (ListView)getView().findViewById(R.id.user_sent_cards);

        listView.setAdapter(adapter);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.user_sent_cards, container, false);


        return view;
    }

    @Override
    public void onUserRegistered(String message) {

    }

    @Override
    public void onUserLoggedIn(String message) {

    }

    @Override
    public void onUserObjectReturned(User user) {

    }

    @Override
    public void onUserObjectByMail(User user) {

    }

    @Override
    public void onUserCompanies(List<Company> companies) {

    }

    @Override
    public void onCompanyRegistered(String message) {

    }

    @Override
    public void onUserUpdated(String message) {

    }

    @Override
    public void onAllUsersDownloaded(List<User> users) {

    }

    @Override
    public void onUserCardsDownloaded(List<Card> cards) {

    }

    @Override
    public void onCardByIdDownloaded(Card card) {

    }

    @Override
    public void onUserCardAdded(String message) {

    }

    @Override
    public void onHistoryAdded(String message) {

    }

    @Override
    public void onSentHistoryDownloaded(List<HistoryEntry> histories) {

    }

    @Override
    public void onReceivedHistoryDownloaded(List<HistoryEntry> histories) {
        ReceivedHistoryTab.this.list = (ArrayList<HistoryEntry>) histories;
        drawList();
        progressDialog.dismiss();
    }
}