package com.jgzuke.intoxicmateandroid.overlay;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;

import com.jgzuke.intoxicmateandroid.tasks.SendCurrentInfoTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jgzuke on 15-06-14.
 */
public class CheckServerService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        try {
            new SendCurrentInfoTask(getApplicationContext(), getCurrentJSONObject()).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public JSONObject getCurrentJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        putLatLong(json);
        putHeartrate(json);
        return json;
    }

    private void putLatLong(JSONObject json) throws JSONException {
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

        if (NetLocationTime < GPSLocationTime) {
            json.put("lat", Double.toString(locationGPS.getLatitude()));
            json.put("lat", Double.toString(locationGPS.getLongitude()));
        } else {
            json.put("lat", Double.toString(locationNet.getLatitude()));
            json.put("lat", Double.toString(locationNet.getLongitude()));
        }
    }

    private void putHeartrate(JSONObject json) throws JSONException {
        json.put("heartrate", "80");
    }
}