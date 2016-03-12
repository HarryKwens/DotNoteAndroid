package com.zhzane.android.dotnoteandroid.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.zhzane.android.dotnoteandroid.DB.DBManager;
import com.zhzane.android.dotnoteandroid.R;
import com.zhzane.android.dotnoteandroid.component.jsapi.JSApi;

@SuppressLint("SetJavaScriptEnabled")
public class StatisticsActivity extends BaseActivity {

    private TextView btnBack;

    private WebView webView;
    private WebSettings webSettings;

    private DBManager mgr;
    private JSApi jsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        mgr = new DBManager(this);

        setButton();
        setWebView();
    }

    private void setButton() {
        btnBack = (TextView) findViewById(R.id.nav_btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private WebView setWebView() {
        webView = (WebView) findViewById(R.id.webView);

        jsApi = new JSApi(StatisticsActivity.this, mgr);

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        webSettings.setDisplayZoomControls(false);

        webView.loadUrl("file:///android_asset/templates/default.html");

        webView.addJavascriptInterface(jsApi, "JSApi");

        return webView;
    }

}
