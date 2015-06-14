package com.jgzuke.intoxicmateandroid.overlay;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;
import com.jgzuke.intoxicmateandroid.R;
import com.jgzuke.intoxicmateandroid.tasks.SendConfirmationTask;

import org.json.JSONException;

/**
 * Created by jgzuke on 15-06-14.
 */
public class OverlayActivity extends AppIntro {
    private static int NUM_INTRO_SCREENS = 3;

    private WarningFragment[] mWarningFragments = {
            new WarningFragmentOpen(),
            new WarningFragmentAction(),
            new WarningFragmentFinish()};

    private ViewPager mPager;
    private Resources mResources;

    private static String mTimestamp;
    private static String mPlaceID;

    @Override
    public void init(Bundle savedInstanceState) {
        mResources = getResources();
        addSlides();
        showSkipButton(false);
        mPager = (ViewPager) findViewById(R.id.view_pager);
    }

    public static void setTimestamp(String timestamp) {
        mTimestamp = timestamp;
    }

    public static void setPlaceID(String placeID) {
        mPlaceID = placeID;
    }

    @Override
    public void onSkipPressed() { }

    @Override
    public void onDonePressed() {
        finish();
    }

    /**
     * Adds slide fragments
     */
    private void addSlides() {
        Context context = getApplicationContext();
        WarningFragment.mOverlayActivity = this;
        for(int i = 0; i < NUM_INTRO_SCREENS; i++) {
            addSlide(mWarningFragments[i], context);
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

    public void setWarningLevel(int level) {
        if(level < 2) {
            finish();
            Log.e("myid", "activityFinished");
        }
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);

        try {
            new SendConfirmationTask(this, level, mTimestamp, mPlaceID).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setAction(int action) {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        if(action == 0) {

        } else if(action == 1) {

        } else if(action == 2) {

        } else if(action == 3) {

        }
    }
}
