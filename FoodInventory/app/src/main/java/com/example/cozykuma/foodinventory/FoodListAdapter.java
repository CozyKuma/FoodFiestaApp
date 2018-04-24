package com.example.cozykuma.foodinventory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

/**
 * Created by CozyKuma on 04-04-2018.
 */

public class FoodListAdapter extends ArrayAdapter<FoodItem> {

    //private static final String TAG = "FoodListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    static class ViewHolder {
        TextView name;
        TextView daysLeft;
    }


    public FoodListAdapter(@NonNull Context context, int resource, @NonNull List<FoodItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Set information
        String name = getItem(position).getItemName();
        int days = getItem(position).getDatesLeft();
        String daysString = "Expires in \n" + days + " days(s)";

        // Create the view loading function
        final View result;

        // Viewholder objects
        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.daysLeft = (TextView) convertView.findViewById(R.id.daysleft);
            //holder.icon = (ImageView) convertView.findViewById(R.id.listitem_image);
            //holder.progress = (ProgressBar) convertView.findViewById(R.id.progress_spinner);
            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position < lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        // using ternary operator (if-else short form)
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(name);
        holder.daysLeft.setText(daysString);

        return convertView;
    }
}
