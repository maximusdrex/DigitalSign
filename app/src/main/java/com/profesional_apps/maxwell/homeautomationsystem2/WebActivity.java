package com.profesional_apps.maxwell.homeautomationsystem2;

import com.profesional_apps.maxwell.homeautomationsystem2.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class WebActivity extends Activity {
    private String[] WebURLS = new String[3];
    private int URL = 0;
    final Handler myHandler = new Handler();
    private WebView webView;
    private String[] URLsArray;
    private int URLCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        webView = (WebView) findViewById(R.id.webViewSystem);
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webViewSystem);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        boolean FileStorgeAvalibilitie = isExternalStorageReadable();
        if (FileStorgeAvalibilitie) {
            File URLs = getURLStorageDir("URLS.txt");
            //URLs.createNewFile();
            URLCount = getLineCount(URLs);
            URLsArray = setURLsArray(URLs, URLCount);
            loop();
        }

    }

    private String[] setURLsArray(File urls, int urlcount) {
        BufferedReader Reader_URLs;
        for (int Line = 0; Line < urlcount; Line++) {

        }
    }

    public int getLineCount(File file) {
        BufferedReader ReaderForURL;
        int Lines = 0;

        try {
            ReaderForURL = new BufferedReader(new FileReader(file));
            while ((ReaderForURL.readLine()) != null) {
                Lines++;
            }
            ReaderForURL.close();
        }
        catch(IOException ex) {

        }
        return Lines;
    }

    public File getURLStorageDir(String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), fileName);
        if (!file.mkdirs()) {
            Log.e("Tag", "Directory not created");
        }
        return file;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            loop();
        }
    };

    private void loop() {
        if (URL == 0) {
            webView.loadUrl("http://www.bing.com/");
        }   else if (URL == 1) {
            webView.loadUrl("http://www.google.com/");
        }   else if (URL == 2) {
            webView.loadUrl("http://www.android.com/");
            URL = -1;
        }
        URL ++;
        Timer timer = new Timer();
        timer.schedule(new TimerTaskLoop(), 60000);
    }


    public class TimerTaskLoop extends TimerTask {
        public void run() {
            myHandler.post(myRunnable);
        }
    }
}
