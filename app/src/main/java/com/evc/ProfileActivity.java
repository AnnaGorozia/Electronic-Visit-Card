package com.evc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        drawProfilePage();

    }

    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain", "Georgia"
    };

    public void editProfile(View view) {
        System.out.println("Edit profile");
    }


    private void drawEditProfile() {
        TextView fullName = (TextView) findViewById(R.id.full_name);
        fullName.setText("Mamuka Sakhelashvili");

        EditText firstName = (EditText) findViewById(R.id.edit_first_name);
        EditText lastName = (EditText) findViewById(R.id.edit_last_name);

        firstName.setText("Mamuka");
        lastName.setText("Sakhelashvili");


        EditText personalPhone = (EditText) findViewById(R.id.edit_phone_number);

        EditText personalEmail = (EditText) findViewById(R.id.personal_email);

        EditText firstCompany = (EditText) findViewById(R.id.edit_company);
        EditText firstCompanyPosition = (EditText) findViewById(R.id.first_company_position);


    }

    private void drawProfilePage() {

        TextView phone = (TextView) findViewById(R.id.phone_number);

        TextView email = (TextView) findViewById(R.id.email);

        TextView company = (TextView) findViewById(R.id.company);

        phone.setText("(598) 125-654");
        email.setText("msakh12@freenui.edu.ge");
        company.setText("Google");
    }

    public void Cancel(View view) {

        System.out.println("Cancel");
    }

    public void Done(View view) {
        System.out.println("Done");
    }
}
