package com.jgzuke.intoxicmateandroid.tasks;

import android.content.Context;
import android.util.Log;

import com.jgzuke.intoxicmateandroid.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jgzuke on 15-06-14.
 */
public class SendConfirmationTask extends BaseSendInfoTask {

    public SendConfirmationTask(Context context, Boolean isDrunk) throws JSONException {
        mContext = context;
        mJSONObject = new JSONObject();
        mJSONObject.put("drunk", isDrunk.toString());
        postUrl = "http://www.yoursite.com/script.php";
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        //TODO check data
        //mActivity.onSendSettingsTaskResult(result);
    }
}
