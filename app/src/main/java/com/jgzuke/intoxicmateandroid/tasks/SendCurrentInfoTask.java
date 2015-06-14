package com.jgzuke.intoxicmateandroid.tasks;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jgzuke.intoxicmateandroid.overlay.OverlayActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jgzuke on 15-06-14.
 */
public class SendCurrentInfoTask extends BaseSendInfoTask {

    private String mTimeStamp;
    private String mPlaceID;

    public SendCurrentInfoTask(Context context, JSONObject JSONObject) {
        mContext = context;
        mJSONObject = JSONObject;
        postUrl = "http://ec2-52-26-100-70.us-west-2.compute.amazonaws.com:8888/data/";
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        try {
            addDateAndUUIDToJSON(mJSONObject);
            mTimeStamp = mJSONObject.getString("timestamp");

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

    @Override
    protected void onPostExecute(JSONObject result) {
        Log.e("myid", "onPostExecute");
        Log.e("myid", result.toString());
        try {
            mPlaceID = result.getString("place_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            boolean isDrunk = result == null? false: result.getBoolean("drunk");
            if(isDrunk) {
                Intent intent = new Intent(mContext, OverlayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                OverlayActivity.setTimestamp(mTimeStamp);
                OverlayActivity.setPlaceID(mPlaceID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}