package com.jgzuke.intoxicmateandroid;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jgzuke.intoxicmateandroid.api.UberAuthTokenClient;
import com.jgzuke.intoxicmateandroid.api.UberCallback;
import com.jgzuke.intoxicmateandroid.model.User;

import retrofit.client.Response;


public class UberActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                UberActivity.this.setProgress(progress * 1000);
            }
        });

        webView.setWebViewClient(new UberWebViewClient());

        webView.loadUrl(buildUrl());
    }

    private String buildUrl() {
        Uri.Builder uriBuilder = Uri.parse(Constants.AUTHORIZE_URL).buildUpon();
        uriBuilder.appendQueryParameter("response_type", "code");
        uriBuilder.appendQueryParameter("client_id", Constants.getUberClientId(this));
        uriBuilder.appendQueryParameter("scope", Constants.SCOPES);
        uriBuilder.appendQueryParameter("redirect_uri", Constants.getUberRedirectUrl(this));
        return uriBuilder.build().toString().replace("%20", "+");
    }

    private class UberWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return checkRedirect(url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (checkRedirect(failingUrl)) {
                return;
            }
            Toast.makeText(UberActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
        }

        private boolean checkRedirect(String url) {
            if (url.startsWith(Constants.getUberRedirectUrl(UberActivity.this))) {
                Uri uri = Uri.parse(url);
                UberAuthTokenClient.getUberAuthTokenClient().getAuthToken(
                        Constants.getUberClientSecret(UberActivity.this),
                        Constants.getUberClientId(UberActivity.this),
                        "authorization_code",
                        uri.getQueryParameter("code"),
                        Constants.getUberRedirectUrl(UberActivity.this),
                        new UberCallback<User>() {
                            @Override
                            public void success(User user, Response response) {
                                DemoActivity.start(UberActivity.this, user.getAccessToken(), user.getTokenType());
                                finish();
                            }
                        });
                return true;
            }
            return false;
        }
    }
}
