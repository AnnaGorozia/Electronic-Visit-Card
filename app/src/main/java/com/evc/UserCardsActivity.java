package com.evc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.evc.adapters.UserCardsAdapter;
import com.evc.models.Card;

import java.util.ArrayList;

/**
 * Created by khrak on 7/26/16.
 */
public class UserCardsActivity extends AppCompatActivity{

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_cards);

        context = this;

        drawCards();
//        Intent intent = new Intent(this, CardsActivity.class);
//        startActivity(intent);
    }

    private void drawCards() {

        ListView listView = (ListView) findViewById(R.id.user_cards_list);


        ArrayList<Card> cards = new ArrayList<Card>();

        Card card1 = new Card("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469465363/m37xlfduohrasf3pfwph.png");
        card1.setId("1");

        Card card2 = new Card("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469525310/aynhujdbsu7h9z31xdta.png");
        card2.setId("2");

        cards.add(card1);
        cards.add(card2);

        UserCardsAdapter userCardsAdapter = new UserCardsAdapter(this, cards);

        listView.setAdapter(userCardsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = (Card) ((UserCardsAdapter)parent.getAdapter()).getItem(position);
                Intent intent = new Intent(context, SendCardActivity.class);
                intent.putExtra("card_id", card.getid());
                startActivity(intent);
            }
        });

    }

    public void addCard(View view) {
        System.out.println("click!");
    }
}
