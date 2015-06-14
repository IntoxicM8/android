package com.jgzuke.intoxicmateandroid.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.jgzuke.intoxicmateandroid.MainActivity;
import com.jgzuke.intoxicmateandroid.intro.IntroActivity;
import com.jgzuke.intoxicmateandroid.overlay.OverlayActivity;

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
public class SendCurrentInfoTask extends BaseSendInfoTask {

    public SendCurrentInfoTask(Context context, JSONObject JSONObject) {
        mContext = context;
        mJSONObject = JSONObject;
        postUrl = "http://www.yoursite.com/script.php";
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        try {
            boolean isDrunk = result == null? false: result.getBoolean(0);
            if(isDrunk) {
                Intent intent = new Intent(mContext, OverlayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}