package com.example.jd158.newsapp;


import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Helper methods related to requesting and receiving news data from USGS.
 */
public final class QueryUtils {

    /**
     * Sample JSON response for a USGS query
     */
    private static final String LOG_TAG = null;


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = null;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            url = createUrl(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            // TODO Handle the IOException
        }

        // Extract relevant fields from the JSON response and create an {@link Book} object
        //ArrayList book = extractFeatureFromJson(jsonResponse);
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the {@link Book} object as the result fo the {@link TsunamiAsyncTask}
        //return books;
        return news;
    }


    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     * @param newsJSON
     */
    public static List<News> extractFeatureFromJson(String newsJSON) {


        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding newss to
        List<News> newsAdd = new ArrayList<>();

        String data = "";
        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Convert SAMPLE_JSON_RESPONSE String into a JSONObject
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            //JSONArray newsArray = baseJsonResponse.getJSONArray("response");
            JSONObject response = baseJsonResponse.getJSONObject("response");
            //Get “results” JSONObject
            JSONArray resultsArray = response.getJSONArray("results");
            int i;

            Date dateObject;
            //SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD,yyyy h:mm a", Locale.ENGLISH);
            // Loop through each feature in the array
            for (i = 0; i < resultsArray.length(); i++) {
                //Get news JSONObject at position i

                JSONObject ArrayObject = resultsArray.getJSONObject(i);


                //Extract “section” for sectionnitude
                String section = ArrayObject.getString("sectionName").toString();

                //Extract “title” for location
                String title = ArrayObject.getString("webTitle").toString();

                String Url = ArrayObject.getString("webUrl").toString();

                String webPublicationDate = ArrayObject.optString("webPublicationDate");
                StringBuilder formattedDate = new StringBuilder(webPublicationDate);
                for ( int j = 0 ;j < webPublicationDate.length(); j++){
                    if (webPublicationDate.charAt(j) == 'T' || webPublicationDate.charAt(j) == 'Z')
                        formattedDate.setCharAt(j,' ');
                }
                webPublicationDate = formattedDate.toString();


                News news = new News(section, title, webPublicationDate, Url);
                newsAdd.add(news);

            }
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of News objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }

        // Return the list of newss
        return newsAdd;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) throws MalformedURLException {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // if the request was successful (response code 200),
            // the read the input strem and parse the response
            if (urlConnection.getResponseCode() == 200) {

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(LOG_TAG, "Error response code:" + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            // TODO: Handle the exception
            Log.e(LOG_TAG, "Problem retrieving the book results", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}