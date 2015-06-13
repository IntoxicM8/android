package com.jgzuke.intoxicmateandroid;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by jgzuke on 15-06-13.
 */
public class IntroActivity extends AppIntro {

    private int mCurrentFrame = 0;
    private TypedArray mBarColorResArray;
    private TypedArray mSeparatorColorResArray;

    @Override
    public void init(Bundle savedInstanceState) {
        addSlides();
        colorSlide();
        showSkipButton(false);
        mBarColorResArray = getResources().obtainTypedArray(R.array.intro_bar_color_array);
        mSeparatorColorResArray = getResources().obtainTypedArray(R.array.intro_separator_color_array);
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
        setBarColor(Color.parseColor(mBarColorResArray.getString(mCurrentFrame)));
        setSeparatorColor(Color.parseColor(mSeparatorColorResArray.getString(mCurrentFrame)));
    }

    @Override
    public void onSkipPressed() {
    }

    @Override
    public void onDonePressed() {
    }
}