package com.evc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.evc.fragments.ProfileTab;
import com.evc.fragments.ReceivedHistoryTab;
import com.evc.fragments.SentHistoryTab;
import com.evc.fragments.UserCardsTab;
import com.evc.login.LoginActivity;
import com.evc.models.Company;
import com.evc.models.User;
import com.evc.tasks.ServerGetCompaniesByUserIdTask;
import com.evc.tasks.ServerGetUserTask;
import com.evc.tasks.UserServiceCompaniesTask;
import com.evc.tasks.UserServiceObjectTask;
import com.evc.transport.NetworkEventListener;
import com.evc.transport.UserInfoDownloaderListener;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkEventListener{

    private static final String USER_COMPANIES = "userCompanies";
    //Tab Variables
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();
    private MyPagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private static ViewPager viewPager;

    private UserInfoDownloaderListener userInfoDownloaderListener;
    private static final String USER_ID = "user_id";
    private static final String NO_LOGED_IN_USER = "-1";
    public static final int LOGIN_CODE = 5;
    private static final String USER = "user";
    private SharedPreferences sharedPref;
    private static User user;
    private static String userId;
    private static List<Company> userCompanies;
    private static Gson gson = new GsonBuilder().create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());

        logPackageName();

        initTabs();

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
            }

            String companiesJson = sharedPref.getString(USER_COMPANIES, NO_LOGED_IN_USER);
            if (!companiesJson.equals(NO_LOGED_IN_USER)) {
                Type listType = new TypeToken<List<Company>>(){}.getType();
                MainActivity.this.userCompanies = gson.fromJson(companiesJson, listType);
            }

        }

    }

    public static List<Company> getUserCompanies() {
        return userCompanies;
    }

    private void logPackageName() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.evc",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
    }

    private void initTabs() {
        ProfileTab profileTab = ProfileTab.newInstance();
        fragmentList.add(profileTab);
        userInfoDownloaderListener = profileTab;

        fragmentList.add(UserCardsTab.newInstance());
        fragmentList.add(SentHistoryTab.newInstance());
        fragmentList.add(ReceivedHistoryTab.newInstance());

        tabTitles.add("Profile");
        tabTitles.add("Cards");
        tabTitles.add("Sent Cards");
        tabTitles.add("Received Cards");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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

                UserServiceCompaniesTask userServiceCompaniesTaks = new ServerGetCompaniesByUserIdTask();
                userServiceCompaniesTaks.setNetworkEventListener(this);
                userServiceCompaniesTaks.execute(taskParams);
            }
        }
    }

    private void init() {
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        userId = sharedPref.getString(USER_ID, NO_LOGED_IN_USER);

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

        userInfoDownloaderListener.onUserInfoDownloaded();



    }

    @Override
    public void onUserCompanies(List<Company> companies) {
        SharedPreferences.Editor editor = sharedPref.edit();
        Type listType = new TypeToken<List<Company>>(){}.getType();
        editor.putString(USER_COMPANIES, gson.toJson(companies, listType));
        editor.apply();
        userInfoDownloaderListener.onUserCompaniesDownloaded();
    }

    @Override
    public void onUserObjectByMail(User user) {

    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            return fragmentList.get(pos);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles.get(position);
        }
    }
}
