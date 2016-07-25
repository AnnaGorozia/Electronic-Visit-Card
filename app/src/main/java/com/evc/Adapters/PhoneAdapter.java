package com.evc.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evc.R;

import java.util.ArrayList;

public class PhoneAdapter extends BaseAdapter {

    private ArrayList<String> list;
    private Context context;

    public PhoneAdapter (Context context, ArrayList<String> list){
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
            rootView = View.inflate(context, R.layout.phone_list_view_item, null);
            ImageView hangout = (ImageView) rootView.findViewById(R.id.hangout_icon);
            TextView phoneNumber = (TextView) rootView.findViewById(R.id.phone_number);
            TextView phoneType = (TextView) rootView.findViewById(R.id.number_type);

            viewHolder = new ViewHolder();
            viewHolder.hangoutIcon = hangout;
            viewHolder.phoneNumber = phoneNumber;
            viewHolder.phoneType = phoneType;
            rootView.setTag(viewHolder);
        }else{
            rootView = convertView;
            viewHolder = (ViewHolder) rootView.getTag();
        }

        String number = (String) getItem(position);

        viewHolder.phoneNumber.setText(number);
        viewHolder.phoneType.setText("Personal");
        viewHolder.hangoutIcon.setImageResource(R.drawable.hangout_icon);

        return rootView;
    }

    private class ViewHolder {
        TextView phoneNumber;
        TextView phoneType;
        ImageView hangoutIcon;
    }
}
