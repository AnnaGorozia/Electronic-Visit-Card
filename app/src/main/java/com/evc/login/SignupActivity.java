package com.evc.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evc.MainActivity;
import com.evc.R;
import com.evc.models.Company;
import com.evc.models.User;
import com.evc.tasks.ServerUserRegisterTask;
import com.evc.tasks.UserServiceTask;
import com.evc.transport.NetworkEventListener;

import java.util.List;

public class SignupActivity extends AppCompatActivity implements NetworkEventListener {

    private static final String TAG = "SignupActivity";

    private EditText nameText;
    private EditText lastNameText;
    private EditText phoneText;
    private EditText emailText;
    private EditText passwordText;
    private Button signupButton;
    private TextView loginLink;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    private void init() {
        nameText = (EditText) findViewById(R.id.input_name);
        lastNameText = (EditText) findViewById(R.id.input_last_name);
        phoneText = (EditText) findViewById(R.id.input_phone);
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = nameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();
        String password = passwordText.getText().toString();

        UserServiceTask userServiceTask = null;
        userServiceTask = new ServerUserRegisterTask();
        userServiceTask.setNetworkEventListener(this);
        String[] taskParams = {name, lastName, email, phone, password};
        userServiceTask.execute(taskParams);


//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//
////                        Client client = ClientBuilder.newClient();
////                        url += "/registrateUser";
////                        User user = new User();
////                        user.setFirstName(name);
////                        user.setLastName(lastName);
////                        user.setEmail(email);
////                        user.setPhone(phone);
////                        user.setPassword(password);
////
////                        String body = gson.toJson(user);
////
////                        Response simulatorResponse = client.target(url)
////                                .request(MediaType.APPLICATION_JSON)
////                                .accept(MediaType.APPLICATION_JSON)
////                                .post(Entity.entity(body, MediaType.APPLICATION_JSON));
////
//
//
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (lastName.isEmpty() || lastName.length() < 3) {
            lastNameText.setError("at least 3 characters");
            valid = false;
        } else {
            lastNameText.setError(null);
        }

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            phoneText.setError("invalid phone number");
            valid = false;
        } else {
            phoneText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onUserRegistered(String message) {
        System.out.println("----------------------  " + message);

        if (message.equals("-1")) {
            onSignupFailed();
        } else {
            MainActivity.setUserId(message);
            onSignupSuccess();
        }

        progressDialog.dismiss();

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
}