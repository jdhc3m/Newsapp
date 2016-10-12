package com.example.jd158.newsapp;

/**
 * Created by jd158 on 11/10/2016.
 */
public class News {

    private String mNewsTitle;

    private String mNewsSection;

    String mTimeInMilliseconds;

    // URL of News page
    private String mUrl;


    public News(String NewsTitle, String Location, String TimeInMilliseconds, String Url)  {
        mNewsTitle = NewsTitle;
        mNewsSection = Location;
        mTimeInMilliseconds = TimeInMilliseconds;
        mUrl = Url;
    }



    public String getNewsTitle(){
        return mNewsTitle;
    }

    public String getNewsSection() {
        return mNewsSection;
    }

    public String getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getUrl() {
        return mUrl;
    }


}
