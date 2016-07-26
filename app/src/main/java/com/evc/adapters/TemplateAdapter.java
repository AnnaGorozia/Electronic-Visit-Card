package com.evc.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.evc.R;
import com.evc.models.Card;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by khrak on 7/26/16.
 */
public class TemplateAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Card> cards;

    public TemplateAdapter(Context context, ArrayList<Card> cards) {
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
        ImageView imageView;

        if(convertView == null){
            rootView = View.inflate(context, R.layout.template_list_view_item, null);

            imageView = (ImageView) rootView.findViewById(R.id.tempalte_png);


            rootView.setTag(imageView);
        }else{
            rootView = convertView;
            imageView = (ImageView) rootView.getTag();
        }

        Card card = (Card) getItem(position);

        Picasso.with(context).load(card.getPath()).into(imageView);

        return rootView;
    }

}
