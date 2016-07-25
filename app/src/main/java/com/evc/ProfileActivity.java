package com.evc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evc.Adapters.CompanyAdapter;
import com.evc.Adapters.EmailAdapter;
import com.evc.Adapters.PhoneAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template1);

        getSupportActionBar().hide();

        Intent intent = new Intent(this, CardsActivity.class);

        startActivity(intent);
    }

    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain", "Georgia"
    };

    public void editProfile(View view) {
        System.out.println("Edit profile");
    }

    public void click(View view) {
        System.out.println("click!");
    }

    public void showPopupMenu(View view){
        System.out.println("clicked!");
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.companies:
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.card_history:
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.received_cards_item:
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.my_cards_item:
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private void drawEditProfile() {
        TextView fullName = (TextView) findViewById(R.id.full_name);
        fullName.setText("Mamuka Sakhelashvili");

        EditText firstName = (EditText) findViewById(R.id.edit_first_name);
        EditText lastName = (EditText) findViewById(R.id.edit_last_name);

        firstName.setText("Mamuka");
        lastName.setText("Sakhelashvili");


        EditText personalPhone = (EditText) findViewById(R.id.personal_phone_number);
        EditText workPhone = (EditText) findViewById(R.id.work_phone_number);

        EditText personalEmail = (EditText) findViewById(R.id.personal_email);
        EditText workEmail = (EditText) findViewById(R.id.work_email_address);

        EditText firstCompany = (EditText) findViewById(R.id.first_company);
        EditText firstCompanyPosition = (EditText) findViewById(R.id.first_company_position);

        EditText secondCompany = (EditText) findViewById(R.id.second_company);
        EditText secondCompanyPosition = (EditText) findViewById(R.id.second_company_position);

        EditText thirdCompany = (EditText) findViewById(R.id.third_company);
        EditText thirdCompanyPosition = (EditText) findViewById(R.id.third_company_position);

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


        ListView companyListview = (ListView) findViewById(R.id.company_list_view);
        ArrayList<String> companies = new ArrayList<>(Arrays.asList("Dropbox",   "Oracle"));

        CompanyAdapter companyAdapter = new CompanyAdapter(this, companies);


        companyListview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                companies.size() * 150));

        companyListview.setAdapter(companyAdapter);

    }

    public void Cancel(View view) {

        System.out.println("Cancel");
    }

    public void Done(View view) {
        System.out.println("Done");
    }
}
