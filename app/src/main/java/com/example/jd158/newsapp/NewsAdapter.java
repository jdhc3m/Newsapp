package com.example.jd158.newsapp;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jd158 on 11/10/2016.
 */
public class NewsAdapter extends ArrayAdapter {


    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);

    }
    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_items, parent, false);
        }

        // Get the {@link Earthquake} object located at this position in the list
        News currentNews = (News) getItem(position);

        // Find the ImageView in the list_item.xml layout with the ID version_name
        TextView titleView = (TextView) listItemView.findViewById(R.id.news_title);

        titleView.setText(currentNews.getNewsTitle());

        // Find the ImageView in the list_item.xml layout with the ID version_name
        TextView sectionView = (TextView) listItemView.findViewById(R.id.section);

        sectionView.setText(currentNews.getNewsSection());

        // Find the ImageView in the list_item.xml layout with the ID version_name
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);

        dateView.setText(currentNews.getTimeInMilliseconds());


        // Create a new Date object from the time in milliseconds of the earthquake
        //String dateObject = new String(currentNews.getTimeInMilliseconds());



        // Format the date string (i.e. "Mar 3, 1984")
        //String formattedDate = formatDate(dateObject);

        // Display the date of the current earthquake in that TextView
        //dateView.setText(formattedDate);

        // Create a new Date object from the time in milliseconds of the earthquake
        //Date timeObject = new Date(currentNews.getTimeInMilliseconds());

        // Find the ImageView in the list_item.xml layout with the ID version_name
        //TextView timeView = (TextView) listItemView.findViewById(R.id.time);

        // Format the date string (i.e. "Mar 3, 1984")
        //String formattedTime = formatTime(timeObject);

        // Display the date of the current earthquake in that TextView
        //timeView.setText(formattedTime);

        return listItemView;

    }

    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);

    }

    private String formatTime(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return dateFormat.format(dateObject);

    }
}