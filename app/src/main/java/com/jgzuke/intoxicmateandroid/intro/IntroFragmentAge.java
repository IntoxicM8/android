package com.jgzuke.intoxicmateandroid.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgzuke.intoxicmateandroid.R;

/**
 * Created by jgzuke on 15-06-13.
 */

public class IntroFragmentAge extends IntroFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro_age, container, false);
        v.findViewById(R.id.age1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.setAge(0);
            }
        });
        v.findViewById(R.id.age2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.setAge(1);
            }
        });
        v.findViewById(R.id.age3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.setAge(2);
            }
        });
        v.findViewById(R.id.age4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.setAge(3);
            }
        });
        return v;
    }
}
