package com.jgzuke.intoxicmateandroid.overlay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jgzuke.intoxicmateandroid.R;
import com.jgzuke.intoxicmateandroid.intro.IntroFragment;

/**
 * Created by jgzuke on 15-06-14.
 */
public class WarningFragmentFinish extends WarningFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_warning_finish, container, false);
        return v;
    }
}
