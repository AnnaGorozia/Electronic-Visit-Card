package com.evc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.evc.login.LoginActivity;
import com.evc.models.User;
import com.evc.tasks.ServerGetUserTask;
import com.evc.tasks.UserServiceObjectTask;
import com.evc.transport.NetworkEventListener;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity implements NetworkEventListener{

    private static final String USER_ID = "user_id";
    private static final String NO_LOGED_IN_USER = "-1";
    public static final int LOGIN_CODE = 5;
    private static final String USER = "user";
    private SharedPreferences sharedPref;
    private static User user;
    private static String userId;
    private TextView helloWorld;
    private static Gson gson = new GsonBuilder().create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());

        init();

        if (userId.equals(NO_LOGED_IN_USER)) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, LOGIN_CODE);
        } else {
            //TODO Open user profile page
            SharedPreferences.Editor editor = sharedPref.edit();

            String userJson = sharedPref.getString(USER, NO_LOGED_IN_USER);
            if (!userJson.equals(NO_LOGED_IN_USER)) {
                MainActivity.this.user = gson.fromJson(userJson, User.class);
                helloWorld.setText("Hello " + user.getFirstName());
            }

        }

    }

    public static User getUser() {
        return user;
    }

    public static void setUserId(String userId) {
        MainActivity.userId = userId;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_CODE) {
            if (resultCode == RESULT_OK) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(USER_ID, userId);
                editor.apply();

                UserServiceObjectTask userServiceTask = null;
                userServiceTask = new ServerGetUserTask();
                userServiceTask.setNetworkEventListener(this);
                String[] taskParams = {userId};
                userServiceTask.execute(taskParams);
            }
        }
    }

    private void init() {
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        userId = sharedPref.getString(USER_ID, NO_LOGED_IN_USER);

        this.helloWorld = (TextView) findViewById(R.id.hello_world);
    }

    @Override
    public void onUserRegistered(String message) {

    }

    @Override
    public void onUserLoggedIn(String message) {

    }

    @Override
    public void onUserObjectReturned(User user) {
        MainActivity.user = user;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER, gson.toJson(user, User.class));
        editor.apply();
        helloWorld.setText("Hello " + user.getFirstName());
    }

    @Override
    public void onUserObjectByMail(User user) {

    }
}
