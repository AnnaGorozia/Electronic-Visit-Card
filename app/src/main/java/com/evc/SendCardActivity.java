package com.evc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * Created by khrak on 7/26/16.
 */
public class SendCardActivity extends AppCompatActivity {
    private static final String[] USERS = new String[] {
            "Mamuka Sakhelashvili", "Anna Gorozia", "Anano Bodokia", "Amiran Ambroladze",
            "Giorgi Bochordishvili", "Mamuka Gasviani", "Anastasia Korolenko", "Anita Rachvelishvili",
            "Anamaria Samkharadze", "Anton Nemsadze", "Andac iyos"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_card);

        Intent intent = getIntent();
        String cardid = intent.getStringExtra("card_id");

        AutoCompleteTextView userField = (AutoCompleteTextView) findViewById(R.id.enter_user_name);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, USERS);

        userField.setAdapter(adapter);
    }

//    public void showPopup(View v) {
//        PopupMenu popup = new PopupMenu(this, v);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.send_card_menu, popup.getMenu());
//        popup.show();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.send_card_menu, menu);
        return true;
    }


    public void bluetoothClick(View view) {
        System.out.println("bluetooth man!");
        Intent intent = new Intent(this, BluetoothScanActivity.class);
        startActivity(intent);
    }
}
