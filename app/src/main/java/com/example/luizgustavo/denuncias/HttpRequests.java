package com.example.luizgustavo.denuncias;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artenes on 29/12/2015.
 */
public class HttpRequests {

    public final static String TAG = HttpRequests.class.getSimpleName();


    public String denuncias () throws MalformedURLException {

        Uri buildUri =
                Uri.parse("http://www.repositoriogit.tecnologia.ws/denuncias?").buildUpon()
                        .appendQueryParameter("cidade", "Santarém")
                        .build();

        URL url = new URL(buildUri.toString());

        return executeRequest(url);

    }

    private String executeRequest (URL url) {

        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            return buffer.toString();

        } catch (IOException error) {

            Log.e(TAG, error.getMessage());
            return null;

        } finally {

            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try{
                    reader.close();
                }  catch (final IOException error) {
                    Log.e(TAG, error.getMessage());
                }
            }

        }

    }



}