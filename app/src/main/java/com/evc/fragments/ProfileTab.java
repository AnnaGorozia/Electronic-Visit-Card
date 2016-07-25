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

import com.evc.Adapters.CompanyAdapter;
import com.evc.Adapters.EmailAdapter;
import com.evc.Adapters.PhoneAdapter;
import com.evc.MainActivity;
import com.evc.R;
import com.evc.models.Company;
import com.evc.models.User;
import com.evc.transport.UserInfoDownloaderListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileTab extends Fragment implements View.OnClickListener, UserInfoDownloaderListener {

    private User user;

    private ImageView editIcon;
    private View view;

    private ListView listView;
    private ListView emailListView;
    private ListView companyListview;
    private TextView nameTextView;

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
        listView = (ListView) getView().findViewById(R.id.phone_list_view);
        emailListView = (ListView) getView().findViewById(R.id.email_list_view);
        companyListview = (ListView) getView().findViewById(R.id.company_list_view);

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
        EditText workPhone = (EditText) getView().findViewById(R.id.work_phone_number);

        EditText personalEmail = (EditText) getView().findViewById(R.id.personal_email);
        EditText workEmail = (EditText) getView().findViewById(R.id.work_email_address);

        EditText firstCompany = (EditText) getView().findViewById(R.id.first_company);
        EditText firstCompanyPosition = (EditText) getView().findViewById(R.id.first_company_position);

        EditText secondCompany = (EditText) getView().findViewById(R.id.second_company);
        EditText secondCompanyPosition = (EditText) getView().findViewById(R.id.second_company_position);

        EditText thirdCompany = (EditText) getView().findViewById(R.id.third_company);
        EditText thirdCompanyPosition = (EditText) getView().findViewById(R.id.third_company_position);

    }

    private void drawUserInfo() {

        nameTextView.setText(MainActivity.getUser() != null ? MainActivity.getUser().getFirstName() : "");


        ArrayList<String> numbers = new ArrayList<>(Arrays.asList(MainActivity.getUser() != null ? MainActivity.getUser().getPhone() : ""));

        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                numbers.size() * 300));

        PhoneAdapter adapter = new PhoneAdapter(getActivity(), numbers);

        listView.setAdapter(adapter);


        ArrayList<String> emails = new ArrayList<>(Arrays.asList(MainActivity.getUser() != null ? MainActivity.getUser().getEmail() : ""));

        EmailAdapter emailAdapter = new EmailAdapter(getActivity(), emails);


        emailListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                emails.size() * 300));

        emailListView.setAdapter(emailAdapter);


    }

    private void drawUserCompanies() {


        ArrayList<String> companies = new ArrayList<>();
        if (MainActivity.getUserCompanies() != null) {
            List<Company> companyList = MainActivity.getUserCompanies();
            for (Company company : companyList) {
                companies.add(company.getName());
            }
        } else {
            companies = new ArrayList<>(Arrays.asList("Dropbox", "Oracle"));
        }

        CompanyAdapter companyAdapter = new CompanyAdapter(getActivity(), companies);


        companyListview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                companies.size() * 300));

        companyListview.setAdapter(companyAdapter);

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

        drawUserInfo();
    }

    @Override
    public void onUserCompaniesDownloaded() {
        if (companyListview == null) return;
        drawUserCompanies();

    }
}