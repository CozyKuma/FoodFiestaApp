package com.example.cozykuma.foodinventory;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ShoppingListAdapter extends ArrayAdapter<ShoppingItem> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    static class ViewHolder {
        TextView name;
        CheckBox checkedBox;
    }

    public ShoppingListAdapter(@NonNull Context context, int resource, @NonNull List<ShoppingItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Set information
        String name = getItem(position).getItemName();
        boolean checked = getItem(position).isChecked();

        // Create the view loading function
        final View result;

        // Viewholder objects
        ShoppingListAdapter.ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ShoppingListAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.nameShopItem);
            holder.checkedBox = (CheckBox) convertView.findViewById(R.id.checkBoxShopItem);


            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ShoppingListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position < lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        // using ternary operator (if-else short form)
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(name);
        holder.checkedBox.setActivated(checked);

        return convertView;
    }
}
