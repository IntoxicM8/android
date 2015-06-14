package com.jgzuke.intoxicmateandroid;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jgzuke.intoxicmateandroid.api.UberAuthTokenClient;
import com.jgzuke.intoxicmateandroid.api.UberCallback;
import com.jgzuke.intoxicmateandroid.model.User;

/**
 * Created by jgzuke on 15-06-13.
 */

public class IntroFragmentUber extends IntroFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_intro_uber, container, false);

        return v;
    }
}
