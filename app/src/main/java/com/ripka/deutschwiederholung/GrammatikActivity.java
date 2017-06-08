package com.ripka.deutschwiederholung;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by ripka on 12/28/16.
 */

public class GrammatikActivity extends NavActivity {
    protected WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewFlipper vf = (ViewFlipper) findViewById(R.id.vf);
        vf.setDisplayedChild(VIEW_GRAMM);

        LinearLayout ctrlPanel = (LinearLayout)findViewById(R.id.web_grammatik_pdf_ctrls);
        ctrlPanel.getBackground().setAlpha(200);

        Button btnZoomIn = (Button)findViewById(R.id.web_grammatik_zc_in);
        btnZoomIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.zoomIn();
                Button zoomOut = (Button)findViewById(R.id.web_grammatik_zc_out);
                zoomOut.setEnabled(true);

                float defaultScale = webView.getHeight() / webView.getContentHeight();
                float currentScale = webView.getScale();
                if (currentScale/defaultScale > 3) {
                    v.setEnabled(false);
                }
            }
        });

        Button btnZoomOut = (Button)findViewById(R.id.web_grammatik_zc_out);
        btnZoomOut.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.zoomOut();
                Button zoomIn = (Button)findViewById(R.id.web_grammatik_zc_in);
                zoomIn.setEnabled(true);

                if (!webView.canZoomOut()) {
                    v.setEnabled(false);
                }
            }
        });
        btnZoomOut.setEnabled(false);

        webView = (WebView) findViewById(R.id.web_grammatik);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        //The default value is true for API level android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 and below,
        //and false for API level android.os.Build.VERSION_CODES.JELLY_BEAN and above.
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
            settings.setAllowUniversalAccessFromFileURLs(true);

        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setWebChromeClient(new MyWebChromeClient());

        loadHtmlWithPdf();

        Button btnNextPage = (Button)findViewById(R.id.web_grammatik_next);
        btnNextPage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript("goNext();", null);
                } else {
                    webView.loadUrl("javascript:goNext();");
                }
                webView.loadUrl("javascript:console.log(document.getElementById('page_info').innerHTML);");
            }
        });

        Button btnPrevPage = (Button)findViewById(R.id.web_grammatik_prev);
        btnPrevPage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    webView.evaluateJavascript("goPrevious();", null);
                } else {
                    webView.loadUrl("javascript:goPrevious();");
                }
                webView.loadUrl("javascript:console.log(document.getElementById('page_info').innerHTML);");
            }
        });
    }
    //	reload on resume
    @Override
    protected void onResume() {
        super.onResume();
        webView.loadUrl( "javascript:window.location.reload( true )" );

    }
    //	clear cache to ensure we have good reload
    @Override
    protected void onPause() {
        super.onPause();
        webView.clearCache(true);
    }

    protected void loadHtmlWithPdf() {
        webView.loadUrl("file:///android_asset/pdfviewer/a1grammatik.html");
    }

    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onConsoleMessage(String message, int lineNumber, String sourceID) {
            //Log.d("LogTag", message);
            message = android.text.Html.fromHtml(message).toString();
            TextView textView = (TextView)findViewById(R.id.web_grammatik_pageCount);
            if ( message.indexOf('%') < 0 && !message.equals("Page: /") )
                textView.setText(message);
        }
    }
}
