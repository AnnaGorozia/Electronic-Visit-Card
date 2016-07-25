package com.evc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.evc.Adapters.CompanyAdapter;
import com.evc.Adapters.EmailAdapter;
import com.evc.Adapters.PhoneAdapter;

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

        getSupportActionBar().hide();



        drawProfilePage();

    }

    private void drawProfilePage() {
        ListView listView = (ListView) findViewById(R.id.phone_list_view);

        ArrayList<String> numbers = new ArrayList<>(Arrays.asList("(521) 465-565", "(557) 224-253"));

        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                numbers.size() * 150));

        PhoneAdapter adapter = new PhoneAdapter(this, numbers);

        listView.setAdapter(adapter);


        ListView emailListView = (ListView) findViewById(R.id.email_list_view);
        ArrayList<String> emails = new ArrayList<>(Arrays.asList("msakh12@freeuni.edu.ge",
                "kakashi@konoha.hoka"));

        EmailAdapter emailAdapter = new EmailAdapter(this, emails);


        emailListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                emails.size() * 150));

        emailListView.setAdapter(emailAdapter);


        ListView companyListview = (ListView) findViewById(R.id.employees_list_view);
        ArrayList<String> companies = new ArrayList<>(Arrays.asList("Mamuka Sakhelashvili",   "Anna Gorozia"));

        CompanyAdapter companyAdapter = new CompanyAdapter(this, companies);


        companyListview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                companies.size() * 150));

        companyListview.setAdapter(companyAdapter);

    }

}
