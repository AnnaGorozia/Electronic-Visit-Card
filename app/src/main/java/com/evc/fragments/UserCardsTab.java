package com.evc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.evc.CreateCardActivity;
import com.evc.MainActivity;
import com.evc.R;
import com.evc.SendCardActivity;
import com.evc.adapters.UserCardsAdapter;
import com.evc.models.Card;
import com.evc.models.Company;
import com.evc.models.History;
import com.evc.models.HistoryEntry;
import com.evc.models.User;
import com.evc.tasks.cardtasks.CardListServiceTask;
import com.evc.tasks.cardtasks.ServerGetAllUserCardsTaskList;
import com.evc.tasks.companytasks.ServerGetCompaniesByUserIdTask;
import com.evc.tasks.companytasks.UserServiceCompaniesTask;
import com.evc.transport.NetworkEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserCardsTab extends Fragment implements View.OnClickListener, NetworkEventListener {

    private Context context;
    private View view;
    private List<Card> cards;
    private UserCardsAdapter userCardsAdapter;
    private ListView listView;

    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public static UserCardsTab newInstance() {
        return new UserCardsTab();
    }

    public UserCardsTab() {

    }


    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_cards, container, false);
        this.view = view;
        context = getContext();

        init();
        drawCards();
        refreshList();

        String[] taskParams = {MainActivity.getUser().getid()};

        CardListServiceTask cardListServiceTask = new ServerGetAllUserCardsTaskList();
        cardListServiceTask.setNetworkEventListener(this);
        cardListServiceTask.execute(taskParams);

        return view;
    }

    private void init() {
        floatingActionButton = (FloatingActionButton) getView().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        cards = new ArrayList<Card>();
    }

    private void drawCards() {

        listView = (ListView) getView().findViewById(R.id.user_cards_list);

//        Card card1 = new Card("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469465363/m37xlfduohrasf3pfwph.png");
//        card1.setId("1");
//
//        Card card2 = new Card("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469525310/aynhujdbsu7h9z31xdta.png");
//        card2.setId("2");
//
//        Card card3 = new Card("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469565460/jburww0i88qjegybn1xg.png");
//        card3.setId("3");
//
//        Card card4 = new Card("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469565525/uzzlevxe0vucvoyeq8nb.png");
//        card4.setId("4");
//
//        cards.add(card1);
//        cards.add(card2);
//        cards.add(card3);
//        cards.add(card4);

        userCardsAdapter = new UserCardsAdapter(getActivity(), (ArrayList<Card>) cards);

        listView.setAdapter(userCardsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = (Card) ((UserCardsAdapter)parent.getAdapter()).getItem(position);
                Intent intent = new Intent(context, SendCardActivity.class);
                intent.putExtra("card_id", card.getid());
                intent.putExtra("card_path", card.getPath());
                startActivity(intent);
            }
        });

    }

    private void refreshList() {
        if (cards != null) {
            if (userCardsAdapter == null) {
                userCardsAdapter = new UserCardsAdapter(this.getActivity(), (ArrayList<Card>) cards);
                listView.setAdapter(userCardsAdapter);
            } else {
                listView.setAdapter(null);
                userCardsAdapter.notifyDataSetChanged();
                listView.setAdapter(userCardsAdapter);
            }
        }
    }

    public void addCard(View view) {
        Intent intent = new Intent(getActivity(), CreateCardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        drawCards();
    }

    @Override
    public void onClick(View v) {
        if (v == floatingActionButton) {
            addCard(getView());
        }
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
        UserCardsTab.this.cards = cards;
        drawCards();
        refreshList();
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

    }
}