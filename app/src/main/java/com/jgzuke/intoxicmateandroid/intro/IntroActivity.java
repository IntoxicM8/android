package com.jgzuke.intoxicmateandroid.intro;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.jgzuke.intoxicmateandroid.ContactPickerActivity;
import com.jgzuke.intoxicmateandroid.LocationPickerActivity;
import com.jgzuke.intoxicmateandroid.R;
import com.jgzuke.intoxicmateandroid.tasks.SendSettingsTask;
import com.jgzuke.intoxicmateandroid.uber.UberActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jgzuke on 15-06-13.
 */
public class IntroActivity extends AppIntro {
    private static int NUM_INTRO_SCREENS = 8;

    private static int CONTACT_PICKER_CODE = 1;
    private static int LOCATION_PICKER_CODE = 2;

    private IntroFragment [] mIntroFragments = {new IntroFragmentWelcome(),
                                                new IntroFragmentAge(),
                                                new IntroFragmentGender(),
                                                new IntroFragmentTolerance(),
                                                new IntroFragmentHome(),
                                                new IntroFragmentUber(),
                                                new IntroFragmentContacts(),
                                                new IntroFragmentFinish()};

    private Integer mAge = null;
    private Boolean mGender = null;
    private Integer mTolerance = null;
    private Integer mTravel = null;
    private Integer mHeartRate = 80;
    private Pair<Double, Double> mHome = null;
    private Pair<String, String> [] mContacts = new Pair [3];

    private ViewPager mPager;

    private Resources mResources;

    @Override
    public void init(Bundle savedInstanceState) {
        mResources = getResources();
        addSlides();
        showSkipButton(false);
        mPager = (ViewPager) findViewById(R.id.view_pager);
    }

    /**
     * Adds slide fragments
     */
    private void addSlides() {
        Context context = getApplicationContext();
        IntroFragment.mIntroActivity = this;
        for(int i = 0; i < NUM_INTRO_SCREENS; i++) {
            addSlide(mIntroFragments[i], context);
        }
    }

    /**
     * captures when frame has been changed
     * @param index current frame
     */
    @Override
    public void selectDot(int index) {
        super.selectDot(index);
        String warningMessage = null;
        switch (index) {
            case 2:
                if(mAge == null) warningMessage = mResources.getString(R.string.intro_warning_empty_age);
                break;
            case 3:
                if(mGender == null) warningMessage = mResources.getString(R.string.intro_warning_empty_gender);
                break;
            case 4:
                if(mTolerance == null) warningMessage = mResources.getString(R.string.intro_warning_empty_tolerance);
                break;
            case 5:
                if(mHome == null) warningMessage = mResources.getString(R.string.intro_warning_empty_home);
                break;
            case 6:
                if(mTravel == null) warningMessage = mResources.getString(R.string.intro_warning_empty_uber);
                break;
            case 7:
                if(mContacts[0] == null) warningMessage = mResources.getString(R.string.intro_warning_empty_contact);
                break;
        }
        if(warningMessage != null) {
            Toast.makeText(this, warningMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void nextPage() {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    public void setAge(int age) {
        mAge = age;
        nextPage();
    }

    public void setGender(boolean gender) {
        mGender = gender;
        nextPage();
    }

    public void setTolerance(int tolerance) {
        mTolerance = tolerance;
        nextPage();
    }

    public void setTravel(int travel) {
        mTravel = travel;
        if(mTravel == 0) {
            startUberSignIn();
        } else {
            nextPage();
        }
    }

    public void startUberSignIn() {
        Intent intent = new Intent(this, UberActivity.class);
        startActivity(intent);
    }

    public void setHome(Pair<Double, Double> home) {
        mHome = home;
        nextPage();
    }

    private int mContactPosition = 0;

    public void startContactPicker(int contactPosition) {
        mContactPosition = contactPosition;
        Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT_PICKER_CODE);
    }

    public void startLocationPicker() {
        Intent intent = new Intent(this, LocationPickerActivity.class);
        startActivityForResult(intent, LOCATION_PICKER_CODE);
    }

    public void selectCurrentLocation() {
        Pair<Double, Double> mapCoords = null;

        LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            mapCoords = new Pair(locationGPS.getLatitude(), locationGPS.getLongitude());
        } else {
            mapCoords = new Pair(locationNet.getLatitude(), locationNet.getLongitude());
        }
        if(mapCoords == null) return;

        setHome(mapCoords);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(intent == null) return;

        if(requestCode == CONTACT_PICKER_CODE) {
            Uri contactData = intent.getData();
            Cursor c =  managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String number = c.getString(c.getColumnIndex(ContactsContract.Contacts.PHONETIC_NAME));
                // TODO Fetch other Contact details as you want to use
                Pair contactInfo = new Pair(number, name);
                setContact(contactInfo, mContactPosition);
            }


        } else if(requestCode == LOCATION_PICKER_CODE) {
            Bundle res = intent.getExtras();
            double mapLat = res.getDouble("map_lat");
            double mapLong = res.getDouble("map_long");
            setHome(new Pair(mapLat, mapLong));
        }
    }

    public void setContact(Pair contact, int position) {
        IntroFragmentContacts contactFrag = (IntroFragmentContacts)mIntroFragments[6];
        String name = (String)contact.second;
        contactFrag.setButtonText(name, position);
        mContacts[position] = contact;
    }

    public void setHeartrate(int heartrate) {
        mHeartRate = heartrate;
    }

    private static final int [] ages = {20, 32, 50, 70};

    private JSONObject getJSON() throws JSONException {
        if(mContacts[1] == null) {
            mContacts[1] = new Pair("empty", "empty");
        }
        if(mContacts[2] == null) {
            mContacts[2] = new Pair("empty", "empty");
        }

        JSONObject json = new JSONObject();
        json.put("age", ages[mAge]);
        json.put("gender", mGender? "male" : "female");
        json.put("tolerance", mTolerance);
        json.put("travel", mTravel);
        json.put("homelat", mHome.first);
        json.put("homelng", mHome.second);
        json.put("bpm", mHeartRate);

        json.put("nameone", mContacts[0].second);
        json.put("numberone", mContacts[0].first);
        json.put("nametwo", mContacts[1].second);
        json.put("numbertwo", mContacts[1].first);
        json.put("namethree", mContacts[2].second);
        json.put("numberthree", mContacts[2].first);
        return json;
    }

    @Override
    public void onSkipPressed() {
    }

    @Override
    public void onDonePressed() {
        int errorID = -1;

        if(mAge == null) {
            errorID = R.string.intro_warning_empty_age;
        } else if(mGender == null) {
            errorID = R.string.intro_warning_empty_gender;
        } else if(mTolerance == null) {
            errorID = R.string.intro_warning_empty_tolerance;
        } else if(mHome == null) {
            errorID = R.string.intro_warning_empty_home;
        } else if(mTravel == null) {
            errorID = R.string.intro_warning_empty_uber;
        } else if(mContacts[0] == null) {
            errorID = R.string.intro_warning_empty_contact;
        }

        if(errorID != -1) {
            Toast.makeText(this, mResources.getString(errorID), Toast.LENGTH_SHORT).show();
        } else {
            try {
                Log.e("myid", "SendSettingsTask");
                new SendSettingsTask(this, getJSON()).execute();
            } catch (JSONException e) {
                Toast.makeText(this, mResources.getString(R.string.send_settings_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onSendSettingsTaskResult(JSONObject result) throws JSONException {
        boolean success = result == null? false: result.getString("shit").equals("works");
        if (success) {
            finish();
        } else {
            Toast.makeText(this, mResources.getString(R.string.send_settings_error), Toast.LENGTH_SHORT).show();
        }
    }
}