package com.jgzuke.intoxicmateandroid.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgzuke.intoxicmateandroid.R;

/**
 * Created by jgzuke on 15-06-13.
 */

public class IntroFragmentTolerance extends IntroFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro_tolerance, container, false);
        v.findViewById(R.id.tolerance1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.setTolerance(0);
            }
        });
        v.findViewById(R.id.tolerance2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.setTolerance(1);
            }
        });
        v.findViewById(R.id.tolerance3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.setTolerance(2);
            }
        });
        return v;
    }
}
