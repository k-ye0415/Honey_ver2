package com.ioad.honey.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ioad.honey.R;
import com.ioad.honey.common.Constant;

public class DaumActivity extends AppCompatActivity {

    WebView wv_daum;

    class MyJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daum);

        wv_daum = findViewById(R.id.wv_daum);
        wv_daum.getSettings().setJavaScriptEnabled(true);
        wv_daum.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

        wv_daum.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                wv_daum.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        wv_daum.loadUrl(Constant.SERVER_IP + "daum.html");

    }
}