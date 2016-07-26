package com.evc.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evc.R;
import com.evc.models.HistoryEntry;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by khrak on 7/27/16.
 */
public class SentCardsAdapter extends BaseAdapter {
    private String prefix;
    private Context context;
    private ArrayList<HistoryEntry> list;

    public SentCardsAdapter(Context context, ArrayList<HistoryEntry> list, String prefix) {
        this.context = context;
        this.list = list;
        this.prefix = prefix;
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
            rootView = View.inflate(context, R.layout.sent_cards_listview_item, null);

            ImageView imageView = (ImageView) rootView.findViewById(R.id.card_png);

            TextView created = (TextView) rootView.findViewById(R.id.created_at);
            TextView partner = (TextView) rootView.findViewById(R.id.partner_full_name);

            viewHolder = new ViewHolder();
            viewHolder.created = created;
            viewHolder.partner = partner;
            viewHolder.cardPng = imageView;

            rootView.setTag(viewHolder);
        }else{
            rootView = convertView;
            viewHolder = (ViewHolder) rootView.getTag();
        }

        HistoryEntry historyEntry = (HistoryEntry) getItem(position);

        viewHolder.created.setText(historyEntry.getDate());
        viewHolder.partner.setText(prefix + " " + historyEntry.getPartner());

        Picasso.with(context).load(historyEntry.getUrl()).into(viewHolder.cardPng);

        return rootView;
    }

    private class ViewHolder {
        TextView created;
        TextView partner;
        ImageView cardPng;
    }
}
