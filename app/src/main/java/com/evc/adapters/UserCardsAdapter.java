package com.evc.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.evc.R;
import com.evc.models.Card;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by khrak on 7/26/16.
 */
public class UserCardsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Card> cards;

    public UserCardsAdapter(Context context, ArrayList<Card> cards) {
        this.context = context;
        this.cards = cards;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
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
            rootView = View.inflate(context, R.layout.user_cards_listview_item, null);

            ImageView imageView = (ImageView) rootView.findViewById(R.id.card_png);

            TextView created = (TextView) rootView.findViewById(R.id.created_at);
            TextView used = (TextView) rootView.findViewById(R.id.used);

            viewHolder = new ViewHolder();
            viewHolder.created = created;
            viewHolder.used = used;
            viewHolder.cardPng = imageView;

            rootView.setTag(viewHolder);
        }else{
            rootView = convertView;
            viewHolder = (ViewHolder) rootView.getTag();
        }

        Card card = (Card) getItem(position);

        viewHolder.created.setText("Created at 12/42/2014");

        Picasso.with(context).load(card.getPath()).into(viewHolder.cardPng);

        return rootView;
    }

    private class ViewHolder {
        TextView created;
        TextView used;
        ImageView cardPng;
    }
}
