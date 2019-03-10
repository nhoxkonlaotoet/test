package com.example.admin.hunghiep_testtiki;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 3/8/2019.
 */

public class DataParser {
    public String[] parse(String jsonData) {
        String[] keywords = new String[0];
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("key");
            //Log.d( "parse count: ", jsonArray.length()+"");
            int n=jsonArray.length();
            keywords = new String[n];
            for(int i=0;i<n;i++) {
                keywords[i]=jsonArray.getString(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return keywords;
    }

}
