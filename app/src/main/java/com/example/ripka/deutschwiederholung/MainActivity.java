package com.example.ripka.deutschwiederholung;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkTest(View view) {
        //logic for checking test
        TextView txtWordCurrent = (TextView) findViewById(R.id.word_of_test);
        String strWord = txtWordCurrent.getText().toString();
        //~logic for checking test

        Button btnCheck = (Button)findViewById(R.id.app_btn_go);
        btnCheck.setVisibility(View.INVISIBLE);

        Button btnNext = (Button)findViewById(R.id.app_btn_next);
        btnNext.setVisibility(View.VISIBLE);
    }

    public void nextTest(View view) {
        //logic for next test
        //~logic for next test
        
        Button btnCheck = (Button)findViewById(R.id.app_btn_go);
        btnCheck.setVisibility(View.VISIBLE);

        Button btnNext = (Button)findViewById(R.id.app_btn_next);
        btnNext.setVisibility(View.INVISIBLE);
    }

    public void onCheckedChanged(View view)
    {
        TextView txtWordTranslation = (TextView)findViewById(R.id.word_translate);
        if ( ((CheckBox)view).isChecked() ) {
            txtWordTranslation.setVisibility(View.VISIBLE);
        } else {
            txtWordTranslation.setVisibility(View.INVISIBLE);
        }
    }
}
