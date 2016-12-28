package com.ripka.deutschwiederholung;

/**
 * Created by ripka on 12/28/16.
 */

public class GrammatikA1Activity extends GrammatikActivity {
    @Override
    protected void loadHtmlWithPdf() {
        webView.loadUrl("file:///android_asset/pdfviewer/a1grammatik.html");
    }
}
