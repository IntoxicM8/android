package com.jgzuke.intoxicmateandroid.tasks;

import android.content.Intent;
import android.util.Log;

import com.jgzuke.intoxicmateandroid.MainActivity;
import com.jgzuke.intoxicmateandroid.overlay.OverlayActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

/**
 * Created by jgzuke on 15-06-14.
 */
public class GetDataTask extends BaseSendInfoTask {

    private OverlayActivity mOverlayActivity;
    private MainActivity mMainActivity;

    public GetDataTask(OverlayActivity overlayActivity, MainActivity mainActivity) {
        mContext = overlayActivity != null? overlayActivity : mainActivity;
        mOverlayActivity = overlayActivity;
        mMainActivity = mainActivity;
        postUrl = "http://ec2-52-26-100-70.us-west-2.compute.amazonaws.com:8888/users/?uuid=1337";
    }

    @Override
    protected JSONObject doInBackground(Void... params) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(postUrl));
            HttpResponse response = client.execute(request);
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
            boolean isDrunk = result == null? false: result.getBoolean("drunk");
            //mActivity;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}