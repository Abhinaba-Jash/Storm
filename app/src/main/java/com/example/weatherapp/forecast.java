package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class forecast extends AppCompatActivity {
    private static String TAG="WebActivity";
WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        webView=(WebView) findViewById(R.id.webView);
        Intent intent=getIntent();
        if(intent!=null){
        String url=intent.getStringExtra("openUrl");
        webView.loadUrl(url);

        }
    }
}