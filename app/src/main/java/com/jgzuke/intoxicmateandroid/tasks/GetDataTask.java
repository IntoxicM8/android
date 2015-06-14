package com.jgzuke.intoxicmateandroid.tasks;

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

    public static final int CALL_EMERGENCY_CONTACT = 1;

    private OverlayActivity mOverlayActivity;
    private MainActivity mMainActivity;
    private int mRequestCode;

    public GetDataTask(OverlayActivity overlayActivity, MainActivity mainActivity, int requestCode) {
        mContext = overlayActivity != null? overlayActivity : mainActivity;
        mOverlayActivity = overlayActivity;
        mMainActivity = mainActivity;
        mRequestCode = requestCode;
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
        if(mOverlayActivity != null) {
            mOverlayActivity.getData(result, mRequestCode);
        }
        if(mMainActivity != null) {
            mMainActivity.getData(result, mRequestCode);
        }
    }
}