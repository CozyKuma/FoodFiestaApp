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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

/**
 * Created by CozyKuma on 04-04-2018.
 */

public class FoodListAdapter extends ArrayAdapter<FoodItem> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    static class ViewHolder {
        TextView name;
        TextView daysLeft;
        ImageView image;
        TextView category;
        TextView progressValue;
        ProgressBar progressBar;
    }


    public FoodListAdapter(@NonNull Context context, int resource, @NonNull List<FoodItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        setupImageLoader();

        FoodCategory testCat = new FoodCategory("Test", 5);

        // Set information
        String name = getItem(position).getItemName();
        int days = getItem(position).getDaysLeft();
        FoodCategory category = getItem(position).getCategory();
        String imgURL = getItem(position).getCategory().getImage();
        String daysString = "Expires in \n" + days + " days(s)";
        String amountLeft = getItem(position).getAmountLeft() + "%";
        int intAmountLeft = getItem(position).getAmountLeft();


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
            holder.image = (ImageView) convertView.findViewById(R.id.catImage);
            holder.category = (TextView) convertView.findViewById(R.id.category);
            holder.progressValue = (TextView) convertView.findViewById(R.id.progressValue);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
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

        int defaultImage = mContext.getResources().getIdentifier("@drawable/default128px", null, mContext.getPackageName());

        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        imageLoader.displayImage(imgURL, holder.image, options);

        holder.name.setText(name);
        holder.daysLeft.setText(daysString);
        holder.category.setText(category.getCategoryName());
        holder.progressValue.setText(amountLeft);
        holder.progressBar.setProgress(intAmountLeft);

        return convertView;
    }

    private void setupImageLoader() {
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }



}
