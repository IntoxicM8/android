package com.jgzuke.intoxicmateandroid.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgzuke.intoxicmateandroid.R;

/**
 * Created by jgzuke on 15-06-13.
 */

public class IntroFragmentUber extends IntroFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro_uber, container, false);
        v.findViewById(R.id.travel1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.setTravel(0);
            }
        });
        v.findViewById(R.id.travel2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.setTravel(1);
            }
        });
        v.findViewById(R.id.travel3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.setTravel(2);
            }
        });
        return v;
    }
}
