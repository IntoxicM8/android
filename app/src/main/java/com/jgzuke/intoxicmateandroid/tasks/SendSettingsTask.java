package com.jgzuke.intoxicmateandroid.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.jgzuke.intoxicmateandroid.intro.IntroActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import java.net.URI;

/**
 * Created by jgzuke on 15-06-14.
 */
public class SendSettingsTask extends BaseSendInfoTask {

    private IntroActivity mActivity;

    public SendSettingsTask(IntroActivity activity, JSONObject JSONObject) {
        mJSONObject = JSONObject;
        mActivity = activity;
        mContext = activity;
        postUrl = "http://ec2-52-26-100-70.us-west-2.compute.amazonaws.com:8888/users/";
        Log.e("myid", "IntroActivity");
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
        try {
            addDateAndUUIDToJSON(mJSONObject);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(postUrl);

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
    protected void onPostExecute(JSONArray result) {
        Log.e("myid", "onPostExecute");
        try {
            mActivity.onSendSettingsTaskResult(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}