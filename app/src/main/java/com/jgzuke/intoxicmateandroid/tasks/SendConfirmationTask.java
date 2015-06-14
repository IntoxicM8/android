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

    public SendConfirmationTask(Context context, Boolean isDrunk) throws JSONException {
        mContext = context;
        mJSONObject = new JSONObject();
        mJSONObject.put("drunk", isDrunk.toString());
        postUrl = "http://ec2-52-26-100-70.us-west-2.compute.amazonaws.com:8888/confirms/";
    }

    @Override
    protected JSONArray doInBackground(Void... params) {
        try {
            Log.e("myid", "testing");

            addDateAndUUIDToJSON(mJSONObject);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(postUrl);

            Log.e("myid", "testing2");
            Log.e("myid", mJSONObject.toString());
            Log.e("myid", "testing3");

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

            // parsing data
            return new JSONArray(result11);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        //TODO check data
        //mActivity.onSendSettingsTaskResult(result);
    }
}
