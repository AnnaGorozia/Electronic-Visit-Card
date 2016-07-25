package com.evc.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.evc.R;

import java.util.ArrayList;

public class EmailAdapter extends BaseAdapter {

    private ArrayList<String> list;
    private Context context;

    public EmailAdapter (Context context, ArrayList<String> list){
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
            rootView = View.inflate(context, R.layout.email_list_view_item, null);
            TextView email = (TextView) rootView.findViewById(R.id.email);
            TextView emailType = (TextView) rootView.findViewById(R.id.email_type);

            viewHolder = new ViewHolder();
            viewHolder.email = email;
            viewHolder.emailType = emailType;
            rootView.setTag(viewHolder);
        }else{
            rootView = convertView;
            viewHolder = (ViewHolder) rootView.getTag();
        }

        String email = (String) getItem(position);

        viewHolder.email.setText(email);
        viewHolder.emailType.setText("Personal");

        return rootView;
    }

    private class ViewHolder {
        TextView email;
        TextView emailType;
    }
}
