package com.evc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.evc.fragments.ProfileTab;
import com.evc.fragments.ReceivedHistoryTab;
import com.evc.fragments.SentHistoryTab;
import com.evc.fragments.UserCardsTab;
import com.evc.login.LoginActivity;
import com.evc.models.Card;
import com.evc.models.Company;
import com.evc.models.History;
import com.evc.models.HistoryEntry;
import com.evc.models.User;
import com.evc.tasks.companytasks.ServerGetCompaniesByUserIdTask;
import com.evc.tasks.usertasks.ServerGetUserTask;
import com.evc.tasks.companytasks.UserServiceCompaniesTask;
import com.evc.tasks.usertasks.UserServiceObjectTask;
import com.evc.transport.NetworkEventListener;
import com.evc.transport.UserInfoDownloaderListener;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    public static final String USER = "user";
    public static SharedPreferences sharedPref;
    private static User user;
    private static String userId;
    private static List<Company> userCompanies;
    private static Gson gson = new GsonBuilder().create();

    public static String url = "http://192.168.0.110:8787/";

    public static Context getContext() {
        return MainActivity.getContext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        return true;
    }

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

    public static void setUser(User user) {
        MainActivity.user = user;
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

    public static void switchToCardsTab(){
        viewPager.setCurrentItem(1);
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
        MainActivity.this.userCompanies = companies;
        userInfoDownloaderListener.onUserCompaniesDownloaded();
    }

    @Override
    public void onCompanyRegistered(String message) {

    }

    @Override
    public void onUserUpdated(String message) {

    }

    @Override
    public void onAllUsersDownloaded(List<User> users) {

    }

    @Override
    public void onUserCardsDownloaded(List<Card> cards) {

    }

    @Override
    public void onCardByIdDownloaded(Card card) {

    }

    @Override
    public void onUserCardAdded(String message) {

    }

    @Override
    public void onHistoryAdded(String message) {

    }

    @Override
    public void onSentHistoryDownloaded(List<HistoryEntry> histories) {

    }

    @Override
    public void onReceivedHistoryDownloaded(List<HistoryEntry> histories) {

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                MainActivity.this.user = null;
                MainActivity.this.userCompanies = null;
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(USER_COMPANIES, NO_LOGED_IN_USER);
                editor.apply();
                editor.putString(USER, NO_LOGED_IN_USER);
                editor.apply();
                editor.putString(USER_ID, NO_LOGED_IN_USER);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent, LOGIN_CODE);
                break;
            default:
                break;
        }

        return true;
    }
}
