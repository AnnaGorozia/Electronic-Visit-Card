package com.evc.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evc.MainActivity;
import com.evc.R;
import com.evc.models.User;
import com.evc.tasks.ServerGetUserByMailTask;
import com.evc.tasks.ServerGetUserTask;
import com.evc.tasks.ServerUserLoginTask;
import com.evc.tasks.ServerUserRegisterTask;
import com.evc.tasks.UserServiceObjectTask;
import com.evc.tasks.UserServiceTask;
import com.evc.transport.NetworkEventListener;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;

public class LoginActivity extends AppCompatActivity implements NetworkEventListener {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog progressDialog;
    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private LoginButton fbLoginButton;
    private TextView signupLink;
    private String[] taskParams;
    private CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    private void init() {
        emailText = (EditText) findViewById(R.id.input_email);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);
        initFb();
    }

    private void initFb() {
        fbLoginButton = (LoginButton) findViewById(R.id.login_button);
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");

        if (fbLoginButton != null) {
            fbLoginButton.setReadPermissions(Collections.singletonList("email"));
        }
        final LoginManager loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        // Application code
                        try {
                            String email = object.getString("email");
                            String firstName = object.getString("first_name");
                            String lastName = object.getString("last_name");
                            System.out.println("-----------------------------" + email + " " + firstName + " " + lastName);

                            taskParams = new String[]{firstName, lastName, email, "599095959", email};
                            UserServiceObjectTask userServiceTask = null;
                            userServiceTask = new ServerGetUserByMailTask();
                            userServiceTask.setNetworkEventListener(LoginActivity.this);
                            String[] taskParams = {email};
                            userServiceTask.execute(taskParams);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email, first_name, last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("------------------------------ on cancel");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("------------------------------ on error");

            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        UserServiceTask userServiceTask = null;
        userServiceTask = new ServerUserLoginTask();
        userServiceTask.setNetworkEventListener(this);
        String[] taskParams = {email, password};
        userServiceTask.execute(taskParams);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        setResult(RESULT_OK);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

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
        if (message.equals("-1")) {
            onLoginFailed();
        } else {
            MainActivity.setUserId(message);
            onLoginSuccess();
        }

        progressDialog.dismiss();
    }

    @Override
    public void onUserLoggedIn(String message) {
        System.out.println("----------------------  " + message);

        if (message.equals("-1")) {
            onLoginFailed();
        } else {
            MainActivity.setUserId(message);
            onLoginSuccess();
        }

        progressDialog.dismiss();
    }

    @Override
    public void onUserObjectReturned(User user) {
    }

    @Override
    public void onUserObjectByMail(User user) {
        if (user != null) {
            MainActivity.setUserId(user.getid());
            onLoginSuccess();
        } else {
            progressDialog.show();
            UserServiceTask userServiceTask = null;
            userServiceTask = new ServerUserRegisterTask();
            userServiceTask.setNetworkEventListener(this);
            userServiceTask.execute(taskParams);
        }
    }
}