package com.example.admin.hunghiep_testtiki.models;

import android.os.AsyncTask;

import com.example.admin.hunghiep_testtiki.DataParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Admin on 3/8/2019.
 */

public class GetKeywordTask extends AsyncTask<Object, String, String> {
    private String data;
    String url;
    OnGetKeywordsFinishedListener listener;

    public GetKeywordTask(OnGetKeywordsFinishedListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Object... objects) {

        url = (String) objects[0];
        try {
            data = readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void getKeywords(String url) {
        this.execute(url);
    }

    @Override
    protected void onPostExecute(String s) {
        //parse json
        DataParser parser = new DataParser();
        String[] keywords = parser.parse(s);
        listener.onGetKeywordsSuccess(keywords);
    }

    public String readUrl(String myUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            //format file json
            sb.append("{\"key\":");
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            //format file json
            sb.append("}");
            data = sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            listener.onGetKeywordsFail(e);
        } catch (IOException e) {
            e.printStackTrace();
            listener.onGetKeywordsFail(e);
        } finally {
            if (inputStream != null)
                inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
