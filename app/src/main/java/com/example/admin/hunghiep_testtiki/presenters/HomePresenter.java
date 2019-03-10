package com.example.admin.hunghiep_testtiki.presenters;

import android.util.Log;

import com.example.admin.hunghiep_testtiki.models.GetKeywordTask;
import com.example.admin.hunghiep_testtiki.models.OnGetKeywordsFinishedListener;
import com.example.admin.hunghiep_testtiki.views.HomeView;

/**
 * Created by Admin on 3/8/2019.
 */

public class HomePresenter implements OnGetKeywordsFinishedListener {
    HomeView view;
    GetKeywordTask getKeywordTask;

    public HomePresenter(HomeView view) {
        this.view = view;
    }

    public void onViewCreated() {
        String url = "https://raw.githubusercontent.com/tikivn/android-home-test/v2/keywords.json";
        getKeywordTask = new GetKeywordTask(this);
        getKeywordTask.getKeywords(url);
    }

    private int getEndlineIndex(String text) {
        int endlineIndex = text.length() - 1;
        if (!text.contains(" "))
            return endlineIndex;
        int n = text.length();
        int left = n / 2;
        int right = left;
        while (left >= 0) {
            if (text.charAt(left) == ' ')
                break;
            left--;
        }
        while (right < n) {
            if (text.charAt(right) == ' ')
                break;
            right++;
        }
        //left is longer |------>
        if (right < (n - left))
            endlineIndex = right;
        else // right is longer <-------|
            endlineIndex = left;
        return endlineIndex;
    }

    private int getLongerLineLength(String text, int endlineIndex) {
        int length;
        int lastIndex = text.length() - 1;
        //set minimum length = 5
        if (lastIndex < 5)
            length = 5;
        else
            // first line is longer
            if (endlineIndex >= lastIndex / 2)
                length = endlineIndex + 1;
            else // second line is longer
                length = lastIndex - endlineIndex;
        return length;
    }

    @Override
    public void onGetKeywordsSuccess(String[] keywords) {
        int n = keywords.length;
        int[] endlineIndexes = new int[n];
        int[] longerLineLengths = new int[n];
        for (int i = 0; i < n; i++) {
            endlineIndexes[i] = getEndlineIndex(keywords[i]);
            longerLineLengths[i] = getLongerLineLength(keywords[i], endlineIndexes[i]);
        }
        view.hideProgressBarWaitKeyword();
        view.showKeywords(keywords, endlineIndexes, longerLineLengths);
    }

    @Override
    public void onGetKeywordsFail(Exception exception) {
        Log.e("onGetKeywordsFail: ", exception.getMessage());
        view.notifyFailure("");
    }
}
