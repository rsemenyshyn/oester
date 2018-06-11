package com.ripka.deutschwiederholung;

/**
 * Created by ripka on 6/11/18.
 */

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class SearchActivity extends NavActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewFlipper vf = (ViewFlipper)findViewById(R.id.vf);
        vf.setDisplayedChild(VIEW_SEARCH);
        afterCreate( savedInstanceState != null );
    }
    protected void afterCreate(boolean isRestored) {
        TextView txtTitle = (TextView) findViewById(R.id.name_activity);
        txtTitle.setText( getResources().getString(R.string.title_activity_search) );
    }
}
