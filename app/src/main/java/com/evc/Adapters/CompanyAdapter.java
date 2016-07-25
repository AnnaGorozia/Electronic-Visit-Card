package com.evc.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.evc.R;

import java.util.ArrayList;

public class CompanyAdapter extends BaseAdapter {

    private ArrayList<String> list;
    private Context context;

    public CompanyAdapter (Context context, ArrayList<String> list){
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
            rootView = View.inflate(context, R.layout.company_list_view_item, null);
            TextView company = (TextView) rootView.findViewById(R.id.company);
            TextView employeeType = (TextView) rootView.findViewById(R.id.position);

            viewHolder = new ViewHolder();
            viewHolder.company = company;
            viewHolder.position = employeeType;
            rootView.setTag(viewHolder);
        }else{
            rootView = convertView;
            viewHolder = (ViewHolder) rootView.getTag();
        }

        String email = (String) getItem(position);

        viewHolder.company.setText(email);
        viewHolder.position.setText("Software Engineer");

        return rootView;
    }

    private class ViewHolder {
        TextView company;
        TextView position;
    }
}
