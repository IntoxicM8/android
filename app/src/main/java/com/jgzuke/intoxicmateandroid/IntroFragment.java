package com.jgzuke.intoxicmateandroid;

import android.support.v4.app.Fragment;

/**
 * Created by jgzuke on 15-06-13.
 */
public abstract class IntroFragment extends Fragment {
    protected IntroActivity mIntroActivity;
    public void setActivity(IntroActivity activity) {
        mIntroActivity = activity;
    }
}
