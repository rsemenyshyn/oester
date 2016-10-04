package com.ripka.deutschwiederholung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_start);

        setContentView(R.layout.activity_start);
        if(savedInstanceState == null){
            afterCreate();
        }
    }
    public void afterCreate() {
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
        AlphaAnimation fadeOut = new AlphaAnimation(0.0f , 1.0f ) ;
        TextView textView = (TextView) findViewById(R.id.loadText);
        textView.startAnimation(fadeIn);
        textView.startAnimation(fadeOut);
        fadeIn.setDuration(3000);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(1000);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(1000+fadeIn.getStartOffset());
    }
    public void btnStart(View view) {
        view.setEnabled(false);
        Intent intent = new Intent(this, NounsActivity.class);
        startActivity(intent);
    }
}
