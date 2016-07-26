package com.evc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.evc.models.User;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button cancel;
    private Button done;

    private EditText firstName;
    private EditText lastName;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText phone;
    private EditText company;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_info);
        user = MainActivity.getUser();
        initButtons();
        init();
    }

    private void init() {
        firstName = (EditText) findViewById(R.id.edit_first_name);
        lastName = (EditText) findViewById(R.id.edit_last_name);
        phone = (EditText) findViewById(R.id.edit_phone_number);
        company = (EditText) findViewById(R.id.edit_company);
        oldPassword = (EditText) findViewById(R.id.user_old_password);
        newPassword = (EditText) findViewById(R.id.user_new_password);
    }

    private void initButtons() {
        cancel = (Button) findViewById(R.id.cancel_edit);
        cancel.setOnClickListener(this);
        done = (Button) findViewById(R.id.done_edit);
        done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == cancel) {
            finish();
        }
        if (v == done) {
            String firstNameEdited = firstName.getText().toString();
            String lastNameEdited = lastName.getText().toString();
            String phoneEdited = phone.getText().toString();
            String companyEdited = company.getText().toString();
            String oldPasswordEntered = oldPassword.getText().toString();
            String newPasswordEntered = newPassword.getText().toString();


        }
    }
}
