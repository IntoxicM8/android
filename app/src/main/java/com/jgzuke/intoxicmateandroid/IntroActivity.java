package com.jgzuke.intoxicmateandroid;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by jgzuke on 15-06-13.
 */
public class IntroActivity extends AppIntro {
    private static int NUM_INTRO_SCREENS = 6;

    private IntroFragment [] mIntroFragments = {new IntroFragmentWelcome(),
                                                new IntroFragmentAge(),
                                                new IntroFragmentGender(),
                                                new IntroFragmentTolerance(),
                                                new IntroFragmentHome(),
                                                new IntroFragmentContacts()};
    private int mCurrentFrame = 0;

    private TypedArray mBarColorResArray;
    private TypedArray mSeparatorColorResArray;

    @Override
    public void init(Bundle savedInstanceState) {
        loadColors();
        addSlides();
        colorSlide();
        showSkipButton(false);
    }

    /**
     * Adds slide fragments
     */
    private void addSlides() {
        Context context = getApplicationContext();
        for(int i = 0; i < NUM_INTRO_SCREENS; i++) {
            mIntroFragments[i].setActivity(this);
            addSlide(new IntroFragmentWelcome(), context);
        }
    }

    private void loadColors() {
        Resources res = getResources();
        mBarColorResArray = res.obtainTypedArray(R.array.intro_bar_color_array);
        mSeparatorColorResArray = res.obtainTypedArray(R.array.intro_separator_color_array);

    }

    /**
     * colors the current slide from resources array
     */
    private void colorSlide() {
        setBarColor(Color.parseColor(mBarColorResArray.getString(mCurrentFrame)));
        setSeparatorColor(Color.parseColor(mSeparatorColorResArray.getString(mCurrentFrame)));

    }

    /**
     * captures when frame has been changed
     * @param index current frame
     */
    @Override
    public void selectDot(int index) {
        super.selectDot(index);
        mCurrentFrame = index;
        colorSlide();
    }

    @Override
    public void onSkipPressed() {
    }

    @Override
    public void onDonePressed() {
        finish();
    }
}