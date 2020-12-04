package com.thecompilers.travelme;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class HotelActivity extends AppCompatActivity {
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);


        load();
        //load the webview
        WebView webView=findViewById(R.id.webViewHotels);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.booking.com");
        webView.setWebViewClient(new WebViewClient(){

            @Override public void onReceivedError(WebView view, WebResourceRequest request,
                                                  WebResourceError error) {
                super.onReceivedError(view, request, error);
                // Do something
            }
        });
    }
    public void load(){
        Toast.makeText(this,"Loading...Booking.com",Toast.LENGTH_SHORT).show();
    }



}