package com.example.webview;

import static com.example.webview.R.id.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.webview.R.id;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;
    EditText search;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView= findViewById(R.id.webView);
        search = findViewById( R.id.search );
        progressBar=findViewById( progress_circular);
        WebSettings webSettings=webView.getSettings();
        webSettings.setSupportZoom(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.getSafeBrowsingEnabled();
        webView.setWebViewClient(new WebViewClient());

                search.setOnKeyListener( new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ( keyCode== EditorInfo.IME_ACTION_SEARCH ||keyCode== EditorInfo.IME_ACTION_DONE||
                                event.getAction()==KeyEvent.ACTION_DOWN&&event.getKeyCode()==KeyEvent.KEYCODE_ENTER ){
                            String searchData = search.getText().toString();
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService( INPUT_METHOD_SERVICE );
                            inputMethodManager.hideSoftInputFromWindow( v.getApplicationWindowToken(),0 );
                            if ( searchData.indexOf( "https://" ) == 0 ) {

                                webView.loadUrl( searchData );

                            }else {

                                webView.loadUrl( "https://google.com/search?q="+searchData );
                            }

                        }

                        return false;
                    }
                } );
        findViewById( R.id.cancelBtn ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText( "" );
            }
        } );
    }

    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
           // super.onBackPressed();
            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Are you exit?");
            builder.setIcon(R.drawable.circle_info_solid);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    public class WebViewClient extends android.webkit.WebViewClient {

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(view.GONE);
        }
    }
}