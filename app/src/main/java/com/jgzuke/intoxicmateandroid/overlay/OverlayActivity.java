package com.jgzuke.intoxicmateandroid.overlay;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.ViewPager;

import com.github.paolorotolo.appintro.AppIntro;
import com.jgzuke.intoxicmateandroid.R;
import com.jgzuke.intoxicmateandroid.tasks.GetDataTask;
import com.jgzuke.intoxicmateandroid.tasks.SendConfirmationTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jgzuke on 15-06-14.
 */
public class OverlayActivity extends AppIntro {
    public static int windowsOpenCount = 0;

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
        windowsOpenCount ++;
        mResources = getResources();
        addSlides();
        showSkipButton(false);
        mPager = (ViewPager) findViewById(R.id.view_pager);
        Vibrator mVibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        mVibrator.vibrate(500);
        Intent i = new Intent();
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Notification.Builder nb = new Notification.Builder(this)
                .setContentTitle("IntoxicM8")
                .setContentText("Are you okay?")
                .setSmallIcon(R.drawable.ic_launcher);

        Intent resultIntent = new Intent(this, OverlayActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        nb.setContentIntent(pi);

        Notification n = nb.build();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, n);
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
        windowsOpenCount --;
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
            windowsOpenCount --;
            finish();
        }
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);

        try {
            new SendConfirmationTask(this, level, mTimestamp, mPlaceID).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(JSONObject result, int requestCode) {
        if(requestCode == GetDataTask.CALL_EMERGENCY_CONTACT) {
            try {
                String phone = "tel:" + result.getString("numberone").replaceAll("[^\\d.]", "");
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if(requestCode == GetDataTask.CALL_EMERGENCY_CONTACT) {
            
        }


    }

    public void setAction(int action) {
        if(action == 0) {

        } else if(action == 1) {

        } else if(action == 2) {

        } else if(action == 3) {
            new GetDataTask(this, null, GetDataTask.CALL_EMERGENCY_CONTACT);
        }
    }
}
