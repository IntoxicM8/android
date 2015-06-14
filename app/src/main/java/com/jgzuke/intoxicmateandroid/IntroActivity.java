package com.jgzuke.intoxicmateandroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Pair;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

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
                                                new IntroFragmentContacts(),
                                                new IntroFragmentUber(),
                                                new IntroFragmentFinish()};

    private Integer mAge = null;
    private Boolean mGender = null;
    private Integer mTolerance = null;
    private String mHome = null;
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

    public void setHome(String home) {
        mHome = home;
        nextPage();
    }

    public void startContactPicker(int contactPosition) {
        Intent intent = new Intent(this, ContactPickerActivity.class);
        intent.putExtra("contact_position", contactPosition);
        startActivityForResult(intent, CONTACT_PICKER_CODE);
    }

    public void startLocationPicker() {
        Intent intent = new Intent(this, LocationPickerActivity.class);
        startActivityForResult(intent, CONTACT_PICKER_CODE);
    }

    public void selectCurrentLocation() {
        Intent intent = new Intent(this, ContactPickerActivity.class);
        intent.putExtra("contact_position", contactPosition);
        startActivityForResult(intent, LOCATION_PICKER_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(intent == null) return;

        if(requestCode == CONTACT_PICKER_CODE) {
            Bundle res = intent.getExtras();
            int contactPosition = res.getInt("contact_position");
            String number = res.getString("contact_number");
            String name = res.getString("contact_name");
            Pair contactInfo = new Pair(number, name);
            setContact(contactInfo, contactPosition);
        } else if(requestCode == LOCATION_PICKER_CODE) {

        }
    }

    public void setContact(Pair contact, int position) {
        IntroFragmentContacts contactFrag = (IntroFragmentContacts)mIntroFragments[5];
        String name = (String)contact.second;
        contactFrag.setButtonText(name, position);
        mContacts[position] = contact;
    }

    @Override
    public void onSkipPressed() {
    }

    @Override
    public void onDonePressed() {
        //TODO sendDataTask
        finish();
    }
}