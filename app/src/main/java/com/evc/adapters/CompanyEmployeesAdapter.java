package com.evc.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.evc.R;
import com.evc.models.User;

import java.util.ArrayList;

/**
 * Created by khrak on 7/27/16.
 */
public class CompanyEmployeesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> list;

    public CompanyEmployeesAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView;
        ViewHolder viewHolder = null;

        if(convertView == null){
            rootView = View.inflate(context, R.layout.company_employee_listview_item, null);

            TextView employee = (TextView) rootView.findViewById(R.id.employee);
            TextView employeePosition = (TextView) rootView.findViewById(R.id.position);

            viewHolder = new ViewHolder();

            viewHolder.employee = employee;
            viewHolder.position = employeePosition;

            rootView.setTag(viewHolder);
        }else{
            rootView = convertView;
            viewHolder = (ViewHolder) rootView.getTag();
        }


        User user = (User) getItem(position);

        viewHolder.employee.setText(user.getFirstName() + " " + user.getLastName());
        viewHolder.position.setText("Software Engineer");

        return rootView;
    }

    private class ViewHolder {
        TextView employee;
        TextView position;
    }
}
