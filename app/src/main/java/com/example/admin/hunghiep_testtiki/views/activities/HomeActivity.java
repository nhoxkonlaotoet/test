package com.example.admin.hunghiep_testtiki.views.activities;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.hunghiep_testtiki.R;
import com.example.admin.hunghiep_testtiki.presenters.HomePresenter;
import com.example.admin.hunghiep_testtiki.views.HomeView;

import java.util.Random;

public class HomeActivity extends AppCompatActivity implements HomeView {
    GridLayout layoutKeyword;
    HomePresenter presenter;
    int height, padding, wordSize, wordSpacing;
    float scale;
    ProgressBar progressBarWaitKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mapping();
        presenter = new HomePresenter(this);
        presenter.onViewCreated();
    }

    void mapping() {
        layoutKeyword = findViewById(R.id.layoutKeyword);
        progressBarWaitKeyword = findViewById(R.id.progressBarWaitingKeyword);
        height = (int) getResources().getDimension(R.dimen.dimen_button_keyword_height);
        wordSize = (int) getResources().getDimension(R.dimen.dimen_word_size);
        wordSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                getResources().getDimension(R.dimen.dimen_word_spacing), getResources().getDisplayMetrics());
        padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                getResources().getDimension(R.dimen.dimen_button_keyword_padding), getResources().getDisplayMetrics());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void showKeywords(String[] keywords, int[] endlineIndexes, int[] longerLineLengths) {
        int n = keywords.length;
        layoutKeyword.setColumnCount(n);
        Button btn;
        // create buttons
        for (int i = 0; i < n; i++) {
            btn = new Button(this);
            setButtonLayoutParams(btn, longerLineLengths[i]);
            setButtonText(btn, keywords[i], endlineIndexes[i]);
            setRandomButtonColor(btn);
            setButtonOnClickListener(btn);
            layoutKeyword.addView(btn, i);
        }
    }

    @Override
    public void hideProgressBarWaitKeyword() {
        progressBarWaitKeyword.setVisibility(View.GONE);
    }

    @Override
    public void setButtonText(Button button, String keyword, int endlineIndex) {
        // set original keyword to search
        button.setTag(keyword);
        StringBuilder formatedKeyword = new StringBuilder();
        int n = keyword.length();
        // only 1 word
        if (endlineIndex == n - 1)
            button.setText(keyword);
        else {
            for (int i = 0; i < n; i++)
                if (i == endlineIndex) //break line
                    formatedKeyword.append('\n');
                else
                    formatedKeyword.append(keyword.charAt(i));
            button.setText(formatedKeyword.toString());
        }
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, wordSize);
        button.setAllCaps(false);
        button.setTextColor(Color.WHITE);
    }

    @Override
    public void setButtonLayoutParams(Button button, int longerLineLength) {
        // button width = text in longer line + padding
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.
                MarginLayoutParams(longerLineLength * (wordSize + wordSpacing) + padding, height);
        button.setLayoutParams(marginLayoutParams);
        marginLayoutParams.setMargins(10, 10, 10, 10);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(marginLayoutParams);
        // align buttons in line
        layoutParams.setGravity(Gravity.TOP);
        button.setLayoutParams(layoutParams);
    }

    @Override
    public void setRandomButtonColor(Button button) {
        Random rnd = new Random();
        int r, g, b;
        //avoid the same color as the text color
        do {
            r = rnd.nextInt(256);
            g = rnd.nextInt(256);
            b = rnd.nextInt(256);
        } while (r > 180 && g > 180 & b > 180);
        int color = Color.rgb(r, g, b);
        Drawable colorDrawable = getResources().getDrawable(R.drawable.button_keyword_background);
        colorDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        button.setBackground(colorDrawable);
    }

    @Override
    public void setButtonOnClickListener(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, button.getTag().toString(), Toast.LENGTH_SHORT).show();
                //go to search
            }
        });
    }

    @Override
    public void notifyFailure(String exMessage) {
        Toast.makeText(this, exMessage, Toast.LENGTH_SHORT).show();
    }
}
