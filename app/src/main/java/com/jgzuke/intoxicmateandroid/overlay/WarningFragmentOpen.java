package com.jgzuke.intoxicmateandroid.overlay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgzuke.intoxicmateandroid.R;

/**
 * Created by jgzuke on 15-06-14.
 */
public class WarningFragmentOpen extends WarningFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_warning_open, container, false);
        v.findViewById(R.id.open1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOverlayActivity.setWarningLevel(0);
            }
        });
        v.findViewById(R.id.open2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOverlayActivity.setWarningLevel(1);
            }
        });
        v.findViewById(R.id.open3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOverlayActivity.setWarningLevel(2);
            }
        });
        return v;
    }
}