package com.jgzuke.intoxicmateandroid.tasks;

import android.content.Context;
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
import java.io.InputStreamReader;

/**
 * Created by jgzuke on 15-06-14.
 */
public class SendConfirmationTask extends BaseSendInfoTask {

    private String mTimestamp;

    public SendConfirmationTask(Context context, int howDrunk, String timestamp) throws JSONException {
        mContext = context;
        mJSONObject = new JSONObject();
        mJSONObject.put("drunk", howDrunk);
        postUrl = "http://ec2-52-26-100-70.us-west-2.compute.amazonaws.com:8888/confirms/";
        mTimestamp = timestamp;
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        try {
            mJSONObject.put("uuid", mUUID);
            mJSONObject.put("timestamp", mTimestamp);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(postUrl);

            Log.e("myid", mJSONObject.toString());

            StringEntity entity = new StringEntity(mJSONObject.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(entity);

            HttpResponse response = httpclient.execute(httppost);
            return getJSONFromResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
