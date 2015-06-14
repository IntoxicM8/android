package com.jgzuke.intoxicmateandroid.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.jgzuke.intoxicmateandroid.MainActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jgzuke on 15-06-14.
 */
public abstract class BaseSendInfoTask extends AsyncTask<Void, Void, JSONArray> {

    protected JSONObject mJSONObject;
    protected String postUrl;
    protected Context mContext;
    protected static String mUUID;

    protected void addDateAndUUIDToJSON(JSONObject json) throws JSONException {
        Log.e("myid", "addDateAndUUIDToJSON");
        json.put("uuid", mUUID);
        Log.e("myid", "test");
        json.put("time", getTime());
        Log.e("myid", "getTime");
    }

    public static void setUUID(String UUID) {
        mUUID = UUID;
    }

    protected String getTime() {
        Calendar c = Calendar.getInstance();
        String time =   Integer.toString(c.get(Calendar.YEAR)) + "-"
                        + Integer.toString(c.get(Calendar.MONTH)) + "-"
                        + Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + " "
                        + Integer.toString(c.get(Calendar.HOUR)) + ":"
                        + Integer.toString(c.get(Calendar.MINUTE)) + ":"
                        + Integer.toString(c.get(Calendar.SECOND));

        Log.e("myid", "time: " + time);
        return time;
    }

    protected JSONArray getJSONFromResponse(HttpResponse response) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
        StringBuilder sb = new StringBuilder();
        sb.append(reader.readLine() + "\n");
        String line = "0";
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        reader.close();
        String result11 = sb.toString();

        Log.e("myid", result11);

        JSONObject jsonObject = new JSONObject(result11);
        JSONArray jsonArray = jsonObject.getJSONArray("");
        return jsonArray;
    }
}