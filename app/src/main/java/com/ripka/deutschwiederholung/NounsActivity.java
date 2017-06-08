package com.ripka.deutschwiederholung;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ripka.deutschwiederholung.models.deTests.NounTest;
import com.ripka.deutschwiederholung.models.deTests.Test;
import com.ripka.deutschwiederholung.models.TestGen;
import com.ripka.deutschwiederholung.models.TestResult;
import com.ripka.deutschwiederholung.models.WordsParser;

public class NounsActivity extends NavActivity {

    protected WordsParser tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewFlipper vf = (ViewFlipper)findViewById(R.id.vf);
        vf.setDisplayedChild(VIEW_NOUNS);

        // ------------- UI setting for first use ------------
        Button btnCheck = (Button)findViewById(R.id.app_btn_go);
        btnCheck.setVisibility(View.VISIBLE);
        btnCheck.setEnabled(false);

        // customizing and setting up toggle buttons (options )
        ViewGroup radioGroup = (ViewGroup)findViewById(R.id.radioGroup);
        int count = radioGroup.getChildCount();
        for (int i=0;i<count;i++) {
            View o = radioGroup.getChildAt(i);
            if (o instanceof ToggleButton) {
                ToggleButton option = (ToggleButton)o;
                option.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        ViewGroup radioGroup = (ViewGroup)findViewById(R.id.radioGroup);
                        int count = radioGroup.getChildCount();
                        for (int i=0;i<count;i++) {
                            View o = radioGroup.getChildAt(i);
                            if (o instanceof ToggleButton) {
                                ToggleButton tb = (ToggleButton)o;
                                tb.setChecked(false);
                            }
                        }
                        ToggleButton btn = (ToggleButton)findViewById( v.getId() );
                        btn.setChecked(true);
                        Button btnCheck = (Button)findViewById(R.id.app_btn_go);
                        btnCheck.setEnabled(true);
                    }
                });
            }
        }

        afterCreate( savedInstanceState != null );
    }
    protected void afterCreate(boolean isRestored) {
        List<Integer> filesToParse = new ArrayList<>(
                Arrays.asList(R.raw.nouns_a1)
        );
        tests = new WordsParser(filesToParse);
        setNextTest(isRestored);
    }

    /* -------------- lower level func ------------*/
    public void onCheckedChanged(View view) {
        TextView txtWordTranslation = (TextView)findViewById(R.id.word_translate);
        if ( ((CheckBox)view).isChecked() ) {
            txtWordTranslation.setVisibility(View.VISIBLE);
        } else {
            txtWordTranslation.setVisibility(View.INVISIBLE);
        }
    }
    public void checkTest(View view) {
        int optionID = 0;
        ViewGroup radioGroup = (ViewGroup)findViewById(R.id.radioGroup);
        int count = radioGroup.getChildCount();
        for (int i=0;i<count;i++) {
            View o = radioGroup.getChildAt(i);
            if (o instanceof ToggleButton) {
                ToggleButton tb = (ToggleButton)o;
                if (tb.isChecked()) {
                    optionID = tb.getId();
                    break;
                }
            }
        }
        View optionButton = radioGroup.findViewById(optionID);
        int idx = radioGroup.indexOfChild(optionButton);

        Test test = TestGen.getLastGeneratedTest();
        TestResult res = ((NounTest)test).getResult( Integer.toString(idx) );

        TextView txtMessage = (TextView) findViewById(R.id.message);
        txtMessage.setText( res.message );
        if (res.isPassed) {
            txtMessage.setBackgroundColor( this.getResources().getColor(R.color.colorSuccess) );
        } else {
            txtMessage.setBackgroundColor( this.getResources().getColor(R.color.colorError) );
        }
        txtMessage.setVisibility(View.VISIBLE);

        Button btnCheck = (Button)findViewById(R.id.app_btn_go);
        btnCheck.setVisibility(View.INVISIBLE);

        Button btnNext = (Button)findViewById(R.id.app_btn_next);
        btnNext.setVisibility(View.VISIBLE);
    }
    public void nextTest(View view) {
        setNextTest(false);

        TextView txtWordTranslation = (TextView)findViewById(R.id.word_translate);
        txtWordTranslation.setVisibility(View.INVISIBLE);

        TextView txtMessage = (TextView) findViewById(R.id.message);
        txtMessage.setVisibility(View.INVISIBLE);

        Button btnNext = (Button)findViewById(R.id.app_btn_next);
        btnNext.setVisibility(View.INVISIBLE);

        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
        if(checkBox.isChecked()){
            checkBox.toggle();
        }

        Button btnCheck = (Button)findViewById(R.id.app_btn_go);
        btnCheck.setVisibility(View.VISIBLE);
        btnCheck.setEnabled(false);
    }
    protected void setNextTest(boolean isRestored){
        Test test = TestGen.getLastGeneratedTest();
        if (!isRestored || test == null) {
            test = TestGen.generateNounTest( tests.getWords() );
        }

        ViewGroup radioGroup = (ViewGroup)findViewById(R.id.radioGroup);
        int count = radioGroup.getChildCount();
        for (int i=0;i<count;i++) {
            View o = radioGroup.getChildAt(i);
            if (o instanceof ToggleButton) {
                ToggleButton option = (ToggleButton)o;
                String strRadio = (i < test.getOptions().size() ) ? test.getOptions().get(i) : "";
                option.setText( strRadio );
                option.setTextOn( strRadio );
                option.setTextOff( strRadio );
                option.setChecked(false);
            }
        }

        TextView txtWordCurrent = (TextView) findViewById(R.id.word_of_test);
        TextView txtWordTranslation = (TextView)findViewById(R.id.word_translate);

        txtWordCurrent.setText( test.getWord() );
        txtWordTranslation.setText( test.getTranslation() );
    }
}
