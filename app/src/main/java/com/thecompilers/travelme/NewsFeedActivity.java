package com.thecompilers.travelme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NewsFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        load();
        //load the webview
        WebView webView=findViewById(R.id.travelNews);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://www.srilanka.travel/travel-news");
        webView.setWebViewClient(new WebViewClient(){

            @Override public void onReceivedError(WebView view, WebResourceRequest request,
                                                  WebResourceError error) {
                super.onReceivedError(view, request, error);
                // Do something
            }
        });
    }
    public void load(){
        Toast.makeText(this,"Loading...srilanka.travel/news",Toast.LENGTH_SHORT).show();
    }
}