package com.jgzuke.intoxicmateandroid.tasks;

import com.jgzuke.intoxicmateandroid.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jgzuke on 15-06-14.
 */
public class SendConfirmationTask extends BaseSendInfoTask {

    private MainActivity mActivity;

    public SendConfirmationTask(MainActivity activity, boolean isDrunk) throws JSONException {
        mJSONObject = new JSONObject();
        mJSONObject.put("drunk", isDrunk? "true" : "false");
        mActivity = activity;
        postUrl = "";
    }

    @Override
    protected void onPostExecute(JSONArray result) {
        //TODO check data
        //mActivity.onSendSettingsTaskResult(result);
    }
}
