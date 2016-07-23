package com.evc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.evc.login.LoginActivity;
import com.evc.login.SignupActivity;
import com.evc.models.User;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID = "user_id";
    private static final String NO_LOGED_IN_USER = "-1";
    public static final int LOGIN_CODE = 5;
    private SharedPreferences sharedPref;
    private static User user;
    private static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (userId.equals(NO_LOGED_IN_USER)) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, LOGIN_CODE);
        } else {
            //TODO Open user profile page
        }

    }

    public static User getUser() {
        return user;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        MainActivity.userId = userId;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_CODE) {
            if (resultCode == RESULT_OK) {

                this.userId = data.getStringExtra("user_id");
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(USER_ID, userId);
                editor.commit();

                //TODO Get user by Id from server
            }
        }
    }

    private void init() {
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        this.userId = sharedPref.getString(USER_ID, NO_LOGED_IN_USER);
    }


}
