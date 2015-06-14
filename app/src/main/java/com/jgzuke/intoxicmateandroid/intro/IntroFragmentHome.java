package com.jgzuke.intoxicmateandroid.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgzuke.intoxicmateandroid.R;

/**
 * Created by jgzuke on 15-06-13.
 */

public class IntroFragmentHome extends IntroFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro_home, container, false);
        v.findViewById(R.id.home1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.selectCurrentLocation();
            }
        });
        v.findViewById(R.id.home2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.startLocationPicker();
            }
        });
        return v;
    }
}
