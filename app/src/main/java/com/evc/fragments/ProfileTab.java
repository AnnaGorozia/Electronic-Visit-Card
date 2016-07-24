package com.evc.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.evc.MainActivity;
import com.evc.R;
import com.evc.adapters.CompanyAdapter;
import com.evc.adapters.EmailAdapter;
import com.evc.adapters.PhoneAdapter;
import com.evc.models.User;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileTab extends Fragment {

    private User user;


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
        TextView nameTextView = (TextView) view.findViewById(R.id.first_name);
        if (MainActivity.getUser() != null) {
            user = MainActivity.getUser();
            nameTextView.setText(user.getFirstName());
        }

        return view;
    }


    public void editProfile(View view) {
        System.out.println("Edit profile");
    }

    public void click(View view) {
        System.out.println("click!");
    }

    public void showPopupMenu(View view){
        System.out.println("clicked!");
        PopupMenu popup = new PopupMenu(getActivity(), getView());
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.companies:
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.card_history:
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.received_cards_item:
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.my_cards_item:
                        Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
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

    private void drawProfilePage() {
        ListView listView = (ListView) getView().findViewById(R.id.phone_list_view);

        ArrayList<String> numbers = new ArrayList<>(Arrays.asList("(521) 465-565", "(557) 224-253"));

        listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                numbers.size() * 150));

        PhoneAdapter adapter = new PhoneAdapter(getActivity(), numbers);

        listView.setAdapter(adapter);


        ListView emailListView = (ListView) getView().findViewById(R.id.email_list_view);
        ArrayList<String> emails = new ArrayList<>(Arrays.asList("msakh12@freeuni.edu.ge",
                "kakashi@konoha.hoka"));

        EmailAdapter emailAdapter = new EmailAdapter(getActivity(), emails);


        emailListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                emails.size() * 150));

        emailListView.setAdapter(emailAdapter);


        ListView companyListview = (ListView) getView().findViewById(R.id.company_list_view);
        ArrayList<String> companies = new ArrayList<>(Arrays.asList("Dropbox",   "Oracle"));

        CompanyAdapter companyAdapter = new CompanyAdapter(getActivity(), companies);


        companyListview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                companies.size() * 150));

        companyListview.setAdapter(companyAdapter);

    }

    public void Cancel(View view) {

        System.out.println("Cancel");
    }

    public void Done(View view) {
        System.out.println("Done");
    }
}