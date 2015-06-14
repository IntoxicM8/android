package com.jgzuke.intoxicmateandroid.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

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
import java.io.InputStreamReader;

/**
 * Created by jgzuke on 15-06-14.
 */
public abstract class BaseSendInfoTask extends AsyncTask<Void, Void, JSONArray> {

    protected JSONObject mJSONObject;
    protected String postUrl;
    protected Context mContext;

    @Override
    protected JSONArray doInBackground(Void... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(postUrl);

            addDateAndUUIDToJSON(mJSONObject);

            StringEntity entity = new StringEntity(mJSONObject.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(entity);

            HttpResponse response = httpclient.execute(httppost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            sb.append(reader.readLine() + "\n");
            String line = "0";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
            String result11 = sb.toString();

            return new JSONArray(result11);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void addDateAndUUIDToJSON(JSONObject json) throws JSONException {
        json.put("uuid", getUUID());
        json.put("time", getTime());
    }

    private String getUUID() {
        TelephonyManager tManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        return tManager.getDeviceId();
    }

    private String getTime() {
        //2015-06-14 03:19:21
    }
}