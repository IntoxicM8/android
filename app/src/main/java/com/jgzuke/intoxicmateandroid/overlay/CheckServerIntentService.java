package com.jgzuke.intoxicmateandroid.overlay;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by jgzuke on 15-06-14.
 */
public class CheckServerIntentService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId){
        if(intent !=null){
            Bundle extras = intent.getExtras();
            ChatHeadsManager.getInstance(this).addChatHead(ct);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}