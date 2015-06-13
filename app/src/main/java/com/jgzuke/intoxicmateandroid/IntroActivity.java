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

    private Integer mAge = null;
    private Boolean mGender = null;
    private Integer mTolerance = null;
    private String mHome = null;
    private String mContactOne = null;
    private String mContactTwo = null;
    private String mContactThree = null;

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
    }

    public void setAge(int age) {
        mAge = age;
    }

    public void setGender(boolean gender) {
        mGender = gender;
    }

    public void setTolerance(int tolerance) {
        mTolerance = tolerance;
    }

    public void setHome(String home) {
        mHome = home;
    }

    public void setContact(String contact, int position) {
        switch (position) {
            case 0:
                mContactOne = contact;
                break;
            case 1:
                mContactTwo = contact;
                break;
            default:
                mContactThree = contact;
                break;
        }
    }

    @Override
    public void onSkipPressed() {
    }

    @Override
    public void onDonePressed() {
        finish();
    }
}