package com.jgzuke.intoxicmateandroid;

import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by jgzuke on 15-06-13.
 */
public class IntroActivity extends AppIntro {

    private int mCurrentFrame = 0;

    private String [] mBarColors = {"#3F51B5", "#3F51B5", "#3F51B5", "#3F51B5", "#3F51B5", "#3F51B5"};
    private String [] mSeparatorColors = {"#2196F3", "#2196F3", "#2196F3", "#2196F3", "#2196F3", "#2196F3"};

    @Override
    public void init(Bundle savedInstanceState) {
        addSlides();
        colorSlide();
        showSkipButton(false);
    }

    /**
     * Adds slide fragments
     */
    private void addSlides() {
        addSlide(new IntroFragmentWelcome(), getApplicationContext());
        addSlide(new IntroFragmentAge(), getApplicationContext());
        addSlide(new IntroFragmentGender(), getApplicationContext());
        addSlide(new IntroFragmentTolerance(), getApplicationContext());
        addSlide(new IntroFragmentHome(), getApplicationContext());
        addSlide(new IntroFragmentContacts(), getApplicationContext());
    }

    private void colorSlide() {
        setBarColor(Color.parseColor(mBarColors[mCurrentFrame]));
        setSeparatorColor(Color.parseColor(mSeparatorColors[mCurrentFrame]));
    }

    @Override
    public void onSkipPressed() {
    }

    @Override
    public void onDonePressed() {
    }
}