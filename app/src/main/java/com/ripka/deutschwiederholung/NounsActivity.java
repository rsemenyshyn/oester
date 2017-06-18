package com.ripka.deutschwiederholung;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ripka.deutschwiederholung.models.deTests.NounTest;
import com.ripka.deutschwiederholung.models.deTests.Test;
import com.ripka.deutschwiederholung.models.TestGen;
import com.ripka.deutschwiederholung.models.TestResult;
import com.ripka.deutschwiederholung.models.WordsParser;

public class NounsActivity extends NavActivity {

    protected WordsParser tests;
    protected Button mOK;
    protected ProgressBar mProgress;
    protected int timerDuration = 10;
    protected Map<Integer,Integer> mTimersMap;
    int pStatus = 0;
    boolean procRun = true;
    private Handler handler = new Handler();

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
        final NounsActivity instance = this;
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
                        btnCheck.setBackgroundColor(instance.getResources().getColor(R.color.colorOK));
                    }
                });
            }
        }

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.progress);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mProgress.setProgressDrawable(drawable);

        mOK = (Button) findViewById(R.id.app_btn_go);

        mTimersMap = new HashMap<Integer,Integer>();
        mTimersMap.put(5, R.id.timer_5);
        mTimersMap.put(10, R.id.timer_10);
        mTimersMap.put(20, R.id.timer_20);
        mTimersMap.put(30, R.id.timer_30);
        TextView txtWord = (TextView) findViewById(mTimersMap.get(timerDuration));
        txtWord.setTextColor(getResources().getColor(R.color.colorOK));

        afterCreate( savedInstanceState != null );
    }
    protected void afterCreate(boolean isRestored) {
        List<Integer> filesToParse = new ArrayList<>(
                Arrays.asList(R.raw.nouns_a1)
        );
        tests = new WordsParser(filesToParse);
        setNextTest(isRestored);
        setProgresBar();
    }

    /* -------------- lower level func ------------*/
    public void toggleViewVisibility(View view) {
        if ( view.getVisibility() != View.VISIBLE ) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }
    public void onOpenTimerSet(View view) {
        View viewSettings = findViewById(R.id.timer_setting);
        toggleViewVisibility( viewSettings );
    }
    public void onTimerSet(View view) {
        if (view instanceof TextView) {
            for(Map.Entry<Integer, Integer> entry : mTimersMap.entrySet()) {
                Integer value = entry.getValue();
                TextView txtTimerBtn = (TextView)findViewById(value);
                txtTimerBtn.setTextColor(getResources().getColor(R.color.colorGreyDark));
            }
            TextView txtTimer = (TextView)view;
            timerDuration = Integer.parseInt( txtTimer.getText().toString() );
            txtTimer.setTextColor(getResources().getColor(R.color.colorOK));
            View viewSettings = findViewById(R.id.timer_setting);
            toggleViewVisibility( viewSettings );
        }
    }
    public void onCheckedChanged(View view) {
        TextView txtWordTranslation = (TextView)findViewById(R.id.word_translate);
        TextView txtWord = (TextView)findViewById(R.id.word_of_test);
        ImageView ivVectorImage = (ImageView) findViewById(R.id.img_translate);
        if ( txtWordTranslation.getVisibility() != View.VISIBLE ) {
            txtWordTranslation.setVisibility(View.VISIBLE);
            txtWord.setPadding(0,0,0,30);
            ivVectorImage.setColorFilter(getResources().getColor(R.color.colorOK));
        } else {
            txtWordTranslation.setVisibility(View.INVISIBLE);
            txtWord.setPadding(0,0,0,0);
            ivVectorImage.setColorFilter(getResources().getColor(R.color.colorGreyDark));
        }
    }
    public void checkTest(View view) {
        int optionID = 0;
        procRun = false;
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

        //TextView txtWordTranslation = (TextView)findViewById(R.id.word_translate);
        //txtWordTranslation.setVisibility(View.INVISIBLE);

        TextView txtMessage = (TextView) findViewById(R.id.message);
        txtMessage.setVisibility(View.INVISIBLE);

        Button btnNext = (Button)findViewById(R.id.app_btn_next);
        btnNext.setVisibility(View.INVISIBLE);

        Button btnCheck = (Button)findViewById(R.id.app_btn_go);
        btnCheck.setVisibility(View.VISIBLE);
        btnCheck.setEnabled(false);
    }
    protected void setNextTest(boolean isRestored) {
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
        setProgresBar();
    }

    protected void setProgresBar() {
        procRun = true;
        pStatus = 0;
        final int loopTime = timerDuration; //seconds
        final int loopStep = 10; //milliseconds
        final int loopsCount = (loopTime * 1000/ loopStep);
        mProgress.setSecondaryProgress(4000);
        mProgress.setMax(loopsCount);
        mProgress.setProgress(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus < loopsCount && procRun) {
                    pStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgress.setProgress(pStatus);
                        }
                    });
                    try {
                        Thread.sleep(loopStep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(procRun) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mOK.callOnClick();
                        }
                    });
                }
            }
        }).start();
    }
}
