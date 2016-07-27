package com.evc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.evc.adapters.CompanyEmployeesAdapter;
import com.evc.models.User;

import java.util.ArrayList;

/**
 * Created by khrak on 7/25/16.
 */
public class CompanyProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_profile);



        drawProfilePage();

    }

    private void drawProfilePage() {
        ListView listView = (ListView) findViewById(R.id.employees_list_view);

        ArrayList<User> list = new ArrayList<User>();

        User user1 = new User("1", "Mamuka", "Sakhelashvili", "msakh" , "d", "", "");
        User user2 = new User("1", "Giorgi", "Sakhelashvili", "msakh" , "d", "", "");
        User user3 = new User("1", "Amiran", "Sakhelashvili", "msakh" , "d", "", "");
        User user4 = new User("1", "Anton", "Nemsadze", "msakh" , "d", "", "");
        User user5 = new User("1", "Zuriko", "Tabatadze", "msakh" , "d", "", "");

        list.add(user2);
        list.add(user1);
        list.add(user3);
        list.add(user4);
        list.add(user5);


        CompanyEmployeesAdapter adapter = new CompanyEmployeesAdapter(this, list);

        listView.setAdapter(adapter);
    }

}
