package com.example.admin.hunghiep_testtiki.models;

/**
 * Created by Admin on 3/8/2019.
 */

public interface OnGetKeywordsFinishedListener {
    void onGetKeywordsSuccess(String[] keywords);
    void onGetKeywordsFail(Exception exception);
}
