package com.jgzuke.intoxicmateandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.jgzuke.intoxicmateandroid.intro.IntroActivity;
import com.jgzuke.intoxicmateandroid.overlay.CheckServerService;


public class MainActivity extends ActionBarActivity {
    private static final long ALARM_PERIOD = 3000; //1800000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }

    private void setUpAlarmManager() {
        AlarmManager manager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        Intent i = new Intent(this, CheckServerService.class);
        PendingIntent receiver = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), ALARM_PERIOD, receiver);
    }
}
