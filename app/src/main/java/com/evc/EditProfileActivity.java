package com.evc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.evc.models.Company;
import com.evc.models.User;
import com.evc.tasks.ServerUserLoginTask;
import com.evc.tasks.ServerUserUpdateTask;
import com.evc.tasks.UserServiceTask;
import com.evc.transport.NetworkEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, NetworkEventListener{

    private Button cancel;
    private Button done;

    private EditText firstName;
    private EditText lastName;
    private EditText oldPassword;
    private EditText newPassword;
    private EditText phone;
    private EditText company;
    private static Gson gson = new GsonBuilder().create();

    private User user;
    private Company userCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_info);
        user = MainActivity.getUser();
        userCompany = MainActivity.getUserCompanies() != null && MainActivity.getUserCompanies().size() > 0 ? MainActivity.getUserCompanies().get(0) : null;
        initButtons();
        init();
        initializeFieldsWithUserInfo();
    }

    private void initializeFieldsWithUserInfo() {
        firstName.setText(user != null ? user.getFirstName() : "");
        lastName.setText(user != null ? user.getLastName() : "");
        phone.setText(user != null ? user.getPhone() : "");
        company.setText(userCompany != null ? userCompany.getName() : "");
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
        if (cancel != null) {
            cancel.setOnClickListener(this);
        }
        done = (Button) findViewById(R.id.done_edit);
        if (done != null) {
            done.setOnClickListener(this);
        }
    }

    public boolean validate() {
        boolean valid = true;

        String name = firstName.getText().toString();
        String lastNameEntered = lastName.getText().toString();
        String phoneEdited = phone.getText().toString();
        String oldPasswordEntered = oldPassword.getText().toString();
        String newPasswordEntered = newPassword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            firstName.setError("at least 3 characters");
            valid = false;
        } else {
            firstName.setError(null);
        }

        if (lastNameEntered.isEmpty() || lastNameEntered.length() < 3) {
            lastName.setError("at least 3 characters");
            valid = false;
        } else {
            lastName.setError(null);
        }

        if (phoneEdited.isEmpty() || !Patterns.PHONE.matcher(phoneEdited).matches()) {
            phone.setError("invalid phone number");
            valid = false;
        } else {
            phone.setError(null);
        }

        if (oldPasswordEntered.isEmpty() || oldPasswordEntered.length() < 4 || oldPasswordEntered.length() > 10 ) {
            oldPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else if (!oldPasswordEntered.equals(user != null ? user.getPassword() : "")){
            oldPassword.setError("passwords don't match");
            valid = false;
        } else {
            oldPassword.setError(null);
        }

        if (newPasswordEntered.isEmpty() || newPasswordEntered.length() < 4 || newPasswordEntered.length() > 10) {
            newPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            newPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        if (v == cancel) {
            finish();
        }
        if (v == done) {
            if (validate()) {
                String firstNameEdited = firstName.getText().toString();
                String lastNameEdited = lastName.getText().toString();
                String phoneEdited = phone.getText().toString();
                String companyEdited = company.getText().toString();
                String oldPasswordEntered = oldPassword.getText().toString();
                String newPasswordEntered = newPassword.getText().toString();

                User user = new User();
                user.setId(MainActivity.getUser().getid());
                user.setFirstName(firstNameEdited);
                user.setLastName(lastNameEdited);
                user.setPhone(phoneEdited);
                user.setPassword(newPasswordEntered);
                user.setEmail(MainActivity.getUser().getEmail());

                MainActivity.setUser(user);

                SharedPreferences.Editor editor = MainActivity.sharedPref.edit();
                editor.putString(MainActivity.USER, gson.toJson(user, User.class));
                editor.apply();

                UserServiceTask userServiceTask = new ServerUserUpdateTask();
                userServiceTask.setNetworkEventListener(this);
                String[] taskParams = {firstNameEdited, lastNameEdited, user.getEmail(), phoneEdited, newPasswordEntered};
                userServiceTask.execute(taskParams);


            }
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
        finish();
    }
}
