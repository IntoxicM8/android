package com.jgzuke.intoxicmateandroid.overlay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgzuke.intoxicmateandroid.R;

/**
 * Created by jgzuke on 15-06-14.
 */
public class WarningFragmentAction extends WarningFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_warning_actions, container, false);
        v.findViewById(R.id.action1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOverlayActivity.setAction(0);
            }
        });
        v.findViewById(R.id.action2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOverlayActivity.setAction(1);
            }
        });
        v.findViewById(R.id.action3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOverlayActivity.setAction(2);
            }
        });
        v.findViewById(R.id.action4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOverlayActivity.setAction(3);
            }
        });
        return v;
    }
}