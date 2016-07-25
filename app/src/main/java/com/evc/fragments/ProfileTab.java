package com.evc.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.evc.MainActivity;
import com.evc.R;
import com.evc.models.Company;
import com.evc.models.User;
import com.evc.transport.UserInfoDownloaderListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileTab extends Fragment implements View.OnClickListener, UserInfoDownloaderListener{

    private User user;

    private ImageView editIcon;
    private View view;

    private ListView listView;
    private ListView emailListView;
    private ListView companyListview;

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
    public void onActivityCreated (Bundle savedInstanceState) {
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
        TextView nameTextView = (TextView) view.findViewById(R.id.first_name);
        if (MainActivity.getUser() != null) {
            user = MainActivity.getUser();
            nameTextView.setText(user.getFirstName() + " " + user.getLastName());
            drawProfilePage();
        }

        return view;
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

    private void drawProfilePage() {



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
        if (listView == null || emailListView == null) return;

        ArrayList<String> numbers = new ArrayList<>(Arrays.asList(user.getPhone()));

        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                numbers.size() * 300));

//        PhoneAdapter adapter = new PhoneAdapter(getActivity(), numbers);
//
//        listView.setAdapter(adapter);
//
//
//        ArrayList<String> emails = new ArrayList<>(Arrays.asList(user.getEmail()));
//
//        EmailAdapter emailAdapter = new EmailAdapter(getActivity(), emails);
//
//
//        emailListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                emails.size() * 300));
//
//        emailListView.setAdapter(emailAdapter);
    }

    @Override
    public void onUserCompaniesDownloaded() {
        if (companyListview == null) return;
        ArrayList<String> companies = new ArrayList<>();
        if (MainActivity.getUserCompanies() != null) {
            List<Company> companyList = MainActivity.getUserCompanies();
            for (Company company : companyList) {
                companies.add(company.getName());
            }
        } else {
            companies = new ArrayList<>(Arrays.asList("Dropbox", "Oracle"));
        }

//        CompanyAdapter companyAdapter = new CompanyAdapter(getActivity(), companies);
//
//
//        companyListview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                companies.size() * 300));
//
//        companyListview.setAdapter(companyAdapter);

    }
}