package com.evc;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.evc.adapters.TemplateAdapter;
import com.evc.models.Card;

import java.util.ArrayList;

/**
 * Created by khrak on 7/26/16.
 */
public class CreateCardActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);

        context = this;

        drawCards();
    }

    private void drawCards() {

        System.out.println("yuy");
        ListView listView = (ListView) findViewById(R.id.templates_list);

        ArrayList<Card> cards = new ArrayList<Card>();

        Card card1 = new Card("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469465363/m37xlfduohrasf3pfwph.png");
        card1.setId("1");

        Card card2 = new Card("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469525310/aynhujdbsu7h9z31xdta.png");
        card2.setId("2");

        cards.add(card1);
        cards.add(card2);


        TemplateAdapter templateAdapter = new TemplateAdapter(this, cards);

        listView.setAdapter(templateAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Card card = (Card) ((TemplateAdapter)parent.getAdapter()).getItem(position);

                System.out.println(card.getid());
//                Intent intent = new Intent(context, SendCardActivity.class);
//                intent.putExtra("card_id", card.getid());
//                startActivity(intent);
            }
        });

    }
}
