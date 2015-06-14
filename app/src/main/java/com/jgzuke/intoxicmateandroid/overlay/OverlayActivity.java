package com.jgzuke.intoxicmateandroid.overlay;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

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

    @Override
    public void init(Bundle savedInstanceState) {
        mResources = getResources();
        addSlides();
        showSkipButton(false);
        mPager = (ViewPager) findViewById(R.id.view_pager);
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Notification.Builder nb = new Notification.Builder(this)
                .setContentTitle("IntoxicM8")
                .setContentText("Are you okay?")
                .setSmallIcon(R.drawable.ic_launcher);
        Notification n = nb.build();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, n);
    }

    public static void setTimestamp(String timestamp) {
        mTimestamp = timestamp;
    }

    @Override
    public void onSkipPressed() { }

    @Override
    public void onDonePressed() {

    }

    /**
     * Adds slide fragments
     */
    private void addSlides() {
        Context context = getApplicationContext();
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
        try {
            new SendConfirmationTask(this, level, mTimestamp).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(level < 2) {
            finish();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }
    }

    public void setAction(int action) {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }
}
