package com.example.cozykuma.foodinventory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * Created by CozyKuma on 04-04-2018.
 */

public class FoodListAdapter extends ArrayAdapter<FoodItem> {

    //private static final String TAG = "FoodListAdapter";

    private Context mContext;
    private int mResource;


    public FoodListAdapter(@NonNull Context context, int resource, @NonNull List<FoodItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String name = getItem(position).getItemName();
        int days = getItem(position).getDatesLeft();
        String daysString = days + " days left";

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.name);
        TextView tvDays = (TextView) convertView.findViewById(R.id.daysleft);

        tvName.setText(name);
        tvDays.setText(daysString);

        return convertView;
    }
}
