package com.evc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.evc.adapters.SentCardsAdapter;
import com.evc.models.HistoryEntry;

import java.util.ArrayList;

/**
 * Created by khrak on 7/27/16.
 */
public class ReceivedCardsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_sent_cards);


        TextView header = (TextView) findViewById(R.id.first_name);

        header.setText("Received cards");


        HistoryEntry entry1 = new HistoryEntry("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469465363/m37xlfduohrasf3pfwph.png",
                "Anton Nemsadze", "12/06/2014");

        HistoryEntry entry2 = new HistoryEntry("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469525310/aynhujdbsu7h9z31xdta.png",
                "Anton Nemsadze", "12/06/2014");

        HistoryEntry entry3 = new HistoryEntry("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469565460/jburww0i88qjegybn1xg.png",
                "Anton Nemsadze", "12/06/2014");

        HistoryEntry entry4 = new HistoryEntry("http://res.cloudinary.com/dpavqa5hs/image/upload/v1469565525/uzzlevxe0vucvoyeq8nb.png",
                "Anton Nemsadze", "12/06/2014");


        ArrayList<HistoryEntry> list = new ArrayList<HistoryEntry>();
        list.add(entry1);
        list.add(entry2);
        list.add(entry3);
        list.add(entry4);

        SentCardsAdapter adapter = new SentCardsAdapter(this, list, "From");

        ListView listView = (ListView)findViewById(R.id.user_sent_cards);

        listView.setAdapter(adapter);
    }
}
