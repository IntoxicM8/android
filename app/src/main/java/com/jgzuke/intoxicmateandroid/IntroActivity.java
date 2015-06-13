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
        addSlides();
        showSkipButton(false);
    }

    /**
     * Adds slide fragments
     */
    private void addSlides() {
        Context context = getApplicationContext();
        for(int i = 0; i < NUM_INTRO_SCREENS; i++) {
            mIntroFragments[i].setActivity(this);
            addSlide(mIntroFragments[i], context);
        }
    }

    /**
     * captures when frame has been changed
     * @param index current frame
     */
    @Override
    public void selectDot(int index) {
        super.selectDot(index);
        mCurrentFrame = index;
    }

    @Override
    public void onSkipPressed() {
    }

    @Override
    public void onDonePressed() {
        finish();
    }
}