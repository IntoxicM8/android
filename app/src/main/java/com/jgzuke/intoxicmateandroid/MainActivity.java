package com.jgzuke.intoxicmateandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.jgzuke.intoxicmateandroid.intro.IntroActivity;
import com.jgzuke.intoxicmateandroid.tasks.BaseSendInfoTask;
import com.jgzuke.intoxicmateandroid.tasks.SendCurrentInfoTask;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {
    private static final long ALARM_PERIOD = 100000L; //1800000L;
    private static final int INTRO_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTaskUUID();
        Intent intent = new Intent(this, IntroActivity.class);
        startActivityForResult(intent, INTRO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == INTRO_REQUEST_CODE) {
            setUpAlarmManager();
        }
    }

    private void setUpAlarmManager() {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override public void onReceive( Context context, Intent _ )
            {
                Log.e("myid", "onReceive");
                launchServerCheck();
            }
        };
        registerReceiver(receiver, new IntentFilter("com.jgzuke.intoxicmate.checkserver"));
        PendingIntent pendingIntent = PendingIntent.getBroadcast( this, 0, new Intent("com.jgzuke.intoxicmate.checkserver"), 0);
        AlarmManager manager = (AlarmManager)(this.getSystemService(Context.ALARM_SERVICE));
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_PERIOD, pendingIntent);
    }

    private void setTaskUUID() {
        TelephonyManager tManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String ID = tManager.getDeviceId();
        Log.e("myid", "testtingUUIDS");
        Log.e("myid", ID);
        BaseSendInfoTask.setUUID(ID);
    }

    private void launchServerCheck() {
        new SendCurrentInfoTask(this, getJSONObject()).execute();
    }

    private JSONObject getJSONObject() {
        JSONObject json = new JSONObject();

        LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) {
            GPSLocationTime = locationGPS.getTime();
        }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        try {
            if (NetLocationTime < GPSLocationTime) {
                json.put("lat", locationGPS.getLatitude());
                json.put("lng", locationGPS.getLongitude());
            } else {
                json.put("lat", locationNet.getLatitude());
                json.put("lng", locationNet.getLongitude());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            json.put("bpm", 80);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
