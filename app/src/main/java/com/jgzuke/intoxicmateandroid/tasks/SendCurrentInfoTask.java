package com.jgzuke.intoxicmateandroid.tasks;

import android.os.AsyncTask;

import com.jgzuke.intoxicmateandroid.MainActivity;
import com.jgzuke.intoxicmateandroid.intro.IntroActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by jgzuke on 15-06-14.
 */
public class SendCurrentInfoTask extends BaseSendInfoTask {

    private MainActivity mActivity;

    public SendCurrentInfoTask(MainActivity activity, JSONObject JSONObject) {
        mJSONObject = JSONObject;
        mActivity = activity;
        postUrl = "";
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        //TODO check data
        //mActivity.onSendSettingsTaskResult(result);
    }
}