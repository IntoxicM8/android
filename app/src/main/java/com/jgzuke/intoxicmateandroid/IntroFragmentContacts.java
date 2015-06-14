package com.jgzuke.intoxicmateandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ButtonFlat;

/**
 * Created by jgzuke on 15-06-13.
 */

public class IntroFragmentContacts extends IntroFragment {
    private static ButtonFlat [] contactButtons = new ButtonFlat[3];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro_contacts, container, false);
        contactButtons[0] = (ButtonFlat) v.findViewById(R.id.contact1);
        contactButtons[1] = (ButtonFlat) v.findViewById(R.id.contact2);
        contactButtons[2] = (ButtonFlat) v.findViewById(R.id.contact3);

        contactButtons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.startContactPicker(0);
            }
        });
        contactButtons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.startContactPicker(1);
            }
        });
        contactButtons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntroActivity.startContactPicker(2);
            }
        });
        return v;
    }

    public void setButtonText(String name, int position) {
        contactButtons[position].setText(name);
    }
}
