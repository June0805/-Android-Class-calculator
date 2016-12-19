package cn.edu.bistu.cs.se.m10;

import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/22.
 */

public class JsInterface {

    private TextView textView;
    private WebView webView;

    public JsInterface(WebView webView, TextView textView){
        this.textView = textView;
        this.webView = webView;
    }

    @JavascriptInterface
    public void javaFunction(final String num){

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                textView.setText(num);
            }
        });
    }

    public void javaCallJsFunction(String operation){
        webView.loadUrl(String.format("javascript:javaCallJsFunction(\"" + operation + "\")"));
    }

}
