package com.evc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by khrak on 7/25/16.
 */
public class CompanyProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_profile);



        drawProfilePage();

    }

    private void drawProfilePage() {
        ListView listView = (ListView) findViewById(R.id.phone_list_view);

        ArrayList<String> numbers = new ArrayList<>(Arrays.asList("(521) 465-565", "(557) 224-253"));



    }

}
