package com.jgzuke.intoxicmateandroid.intro;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

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
public class SendSettingsTask extends AsyncTask<Void, Void, JSONArray> {

    private JSONObject mJSONObject;
    private IntroActivity mActivity;

    public SendSettingsTask(IntroActivity activity, JSONObject JSONObject) {
        mJSONObject = JSONObject;
        mActivity = activity;
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://www.yoursite.com/script.php");

            StringEntity entity = new StringEntity(mJSONObject.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httppost.setEntity(entity);

            // Execute HTTP Post Request
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

            // parsing data
            return new JSONArray(result11);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        mActivity.onSendSettingsTaskResult(result);
    }
}