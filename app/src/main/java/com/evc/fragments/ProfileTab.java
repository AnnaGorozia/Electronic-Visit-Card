package com.evc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evc.MainActivity;
import com.evc.R;
import com.evc.models.Company;
import com.evc.models.User;
import com.evc.transport.UserInfoDownloaderListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileTab extends Fragment implements View.OnClickListener, UserInfoDownloaderListener {

    private ImageView editIcon;
    private View view;

    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView mailTextView;
    private TextView companyTextView;

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public static ProfileTab newInstance() {
        return new ProfileTab();
    }

    public ProfileTab() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_page, container, false);
        this.view = view;
        initButtons(view);
        init();
        drawUserInfo();
        drawUserCompanies();
        return view;
    }

    private void init() {
        nameTextView = (TextView) view.findViewById(R.id.first_name);
        phoneTextView = (TextView) view.findViewById(R.id.phone_number);
        mailTextView = (TextView) view.findViewById(R.id.email);
        companyTextView = (TextView) view.findViewById(R.id.company);
    }

    private void initButtons(View view) {

        editIcon = (ImageView) view.findViewById(R.id.edit_icon);
        editIcon.setOnClickListener(this);

    }


    public void editProfile(View view) {
        System.out.println("Edit profile");
    }

    public void click(View view) {
        System.out.println("click!");
    }

    private void drawEditProfile() {
        TextView fullName = (TextView) getView().findViewById(R.id.full_name);
        fullName.setText("Mamuka Sakhelashvili");

        EditText firstName = (EditText) getView().findViewById(R.id.edit_first_name);
        EditText lastName = (EditText) getView().findViewById(R.id.edit_last_name);

        firstName.setText("Mamuka");
        lastName.setText("Sakhelashvili");


        EditText personalPhone = (EditText) getView().findViewById(R.id.personal_phone_number);

        EditText personalEmail = (EditText) getView().findViewById(R.id.personal_email);

        EditText firstCompany = (EditText) getView().findViewById(R.id.first_company);
        EditText firstCompanyPosition = (EditText) getView().findViewById(R.id.first_company_position);


    }

    private void drawUserInfo() {

        nameTextView.setText(MainActivity.getUser() != null ? MainActivity.getUser().getFirstName() : "");

        phoneTextView.setText(MainActivity.getUser() != null ? MainActivity.getUser().getPhone() : "");

        mailTextView.setText(MainActivity.getUser() != null ? MainActivity.getUser().getEmail() : "");

    }

    private void drawUserCompanies() {


        ArrayList<String> companies = new ArrayList<>();
        if (MainActivity.getUserCompanies() != null) {
            List<Company> companyList = MainActivity.getUserCompanies();
            for (Company company : companyList) {
                companies.add(company.getName());
            }
        } else {
            companies = new ArrayList<>(Arrays.asList("Dropbox"));
        }

        companyTextView.setText(companies.get(0));

    }

    public void Cancel(View view) {

        System.out.println("Cancel");
    }

    public void Done(View view) {
        System.out.println("Done");
    }

    @Override
    public void onClick(View v) {
        if (v == editIcon) {
            editProfile(getView());
        }
    }

    @Override
    public void onUserInfoDownloaded() {
        drawUserInfo();
    }

    @Override
    public void onUserCompaniesDownloaded() {
        drawUserCompanies();

    }
}
