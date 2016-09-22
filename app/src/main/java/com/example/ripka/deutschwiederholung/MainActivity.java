package com.example.ripka.deutschwiederholung;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ripka.deutschwiederholung.models.WordsParser;
import com.example.ripka.deutschwiederholung.models.TestGen;
import com.example.ripka.deutschwiederholung.models.Test;
import com.example.ripka.deutschwiederholung.models.TestResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static private Context context;
    protected WordsParser tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        tests = new WordsParser();

        setNextTest();
    }

    static public Context getMainActivityContext() {
        return context;
    }

    /* -------------- CONTROLLS ------------*/
    public void checkTest(View view) {
        checkCurrTest();

        Button btnCheck = (Button)findViewById(R.id.app_btn_go);
        btnCheck.setVisibility(View.INVISIBLE);

        Button btnNext = (Button)findViewById(R.id.app_btn_next);
        btnNext.setVisibility(View.VISIBLE);

        TextView txtMessage = (TextView) findViewById(R.id.message);
        txtMessage.setVisibility(View.VISIBLE);
    }
    public void nextTest(View view) {
        setNextTest();

        TextView txtWordTranslation = (TextView)findViewById(R.id.word_translate);
        txtWordTranslation.setVisibility(View.INVISIBLE);

        TextView txtMessage = (TextView) findViewById(R.id.message);
        txtMessage.setVisibility(View.INVISIBLE);

        Button btnCheck = (Button)findViewById(R.id.app_btn_go);
        btnCheck.setVisibility(View.VISIBLE);

        Button btnNext = (Button)findViewById(R.id.app_btn_next);
        btnNext.setVisibility(View.INVISIBLE);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
    }
    public void onCheckedChanged(View view) {
        TextView txtWordTranslation = (TextView)findViewById(R.id.word_translate);
        if ( ((CheckBox)view).isChecked() ) {
            txtWordTranslation.setVisibility(View.VISIBLE);
        } else {
            txtWordTranslation.setVisibility(View.INVISIBLE);
        }
    }

    /* ------------------- LOGIC ----------------*/
    protected void setNextTest(){
        Test test = TestGen.generateNextTest( tests.getWords() );

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        int count = radioGroup.getChildCount();
        for (int i=0;i<count;i++) {
            View o = radioGroup.getChildAt(i);
            if (o instanceof RadioButton) {
                RadioButton option = (RadioButton)o;
                String strRadio = (i < test.getOptions().size() ) ? test.getOptions().get(i) : "";
                option.setText( strRadio );
            }
        }

        TextView txtWordCurrent = (TextView) findViewById(R.id.word_of_test);
        TextView txtWordTranslation = (TextView)findViewById(R.id.word_translate);

        txtWordCurrent.setText( test.getWord() );
        txtWordTranslation.setText( test.getTranslation() );
    }
    protected void checkCurrTest(){
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        int idx = radioGroup.indexOfChild(radioButton);

        TestResult res = TestGen.checkTest(idx);
        TextView txtMessage = (TextView) findViewById(R.id.message);
        txtMessage.setText( res.message );
    }
}
