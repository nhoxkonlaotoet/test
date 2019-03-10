package com.example.admin.hunghiep_testtiki.views;

import android.widget.Button;

/**
 * Created by Admin on 3/8/2019.
 */

public interface HomeView {
    void hideProgressBarWaitKeyword();
    void setButtonText(Button button, String keyword, int endlineIndex);
    void setButtonLayoutParams(Button button, int longerLineLength);
    void setRandomButtonColor(Button button);
    void setButtonOnClickListener(Button button);
    void showKeywords(String[] keywords, int[] endlineIndexes, int[] longerLineLengths);
    void notifyFailure(String exMessage);
}
