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
import java.util.Random;

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
    protected Map<Integer,Integer> mTimersMapBtn;
    protected Map<Integer,Integer> mTimersMapBtnHover;
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
        mTimersMap.put(R.id.timer_5, 5);
        mTimersMap.put(R.id.timer_10, 10);
        mTimersMap.put(R.id.timer_20, 20);
        mTimersMap.put(R.id.timer_30, 30);

        mTimersMapBtn = new HashMap<Integer,Integer>();
        mTimersMapBtn.put(5, R.mipmap.ic_time_5);
        mTimersMapBtn.put(10, R.mipmap.ic_time_10);
        mTimersMapBtn.put(20, R.mipmap.ic_time_20);
        mTimersMapBtn.put(30, R.mipmap.ic_time_30);

        mTimersMapBtnHover = new HashMap<Integer,Integer>();
        mTimersMapBtnHover.put(5, R.mipmap.ic_time_5_hover);
        mTimersMapBtnHover.put(10, R.mipmap.ic_time_10_hover);
        mTimersMapBtnHover.put(20, R.mipmap.ic_time_20_hover);
        mTimersMapBtnHover.put(30, R.mipmap.ic_time_30_hover);

        for(Map.Entry<Integer, Integer> entry : mTimersMap.entrySet()) {
            int timeVal = entry.getValue();
            if (timeVal == timerDuration) {
                ImageView imgTimerBtn = (ImageView)findViewById( entry.getKey() );
                imgTimerBtn.setImageResource(mTimersMapBtnHover.get(timeVal));
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
        if (view instanceof ImageView) {
            ImageView timerToggleBtn = (ImageView)view;
            if (viewSettings.getVisibility() != View.VISIBLE) {
                timerToggleBtn.setImageResource(R.mipmap.ic_time_hover);
            } else {
                timerToggleBtn.setImageResource(R.mipmap.ic_time);
            }
        }
        toggleViewVisibility( viewSettings );
    }
    public void onTimerSet(View view) {
        if (view instanceof ImageView) {
            for(Map.Entry<Integer, Integer> entry : mTimersMap.entrySet()) {
                int timeBtnId = entry.getKey();
                int timeVal = entry.getValue();
                ImageView imgTimerBtn = (ImageView)findViewById(timeBtnId);
                imgTimerBtn.setImageResource(mTimersMapBtn.get(timeVal));
            }
            ImageView btnTimer = (ImageView)view;
            int btnId = btnTimer.getId();
            timerDuration = mTimersMap.get(btnId);
            btnTimer.setImageResource(mTimersMapBtnHover.get(timerDuration));
            View viewSettings = findViewById(R.id.timer_setting);
            toggleViewVisibility( viewSettings );
            setProgresBar();
        }
    }
    public void onTranslateToggle(View view) {
        TextView txtWordTranslation = (TextView)findViewById(R.id.word_translate);
        TextView txtWord = (TextView)findViewById(R.id.word_of_test);
        ImageView ivVectorImage = (ImageView) findViewById(R.id.img_translate);
        if ( txtWordTranslation.getVisibility() != View.VISIBLE ) {
            txtWordTranslation.setVisibility(View.VISIBLE);
            txtWord.setPadding(0,0,0,30);
            ivVectorImage.setImageResource(R.mipmap.ic_translate_hover);
        } else {
            txtWordTranslation.setVisibility(View.INVISIBLE);
            txtWord.setPadding(0,0,0,0);
            ivVectorImage.setImageResource(R.mipmap.ic_translate);
        }
    }
    public void onEasyToggle(View view) {
        ImageView ivVectorImage = (ImageView) findViewById(R.id.img_50x50);
        ivVectorImage.setImageResource(R.mipmap.ic_50x50_hover);
        Test test = TestGen.getLastGeneratedTest();
        int correctOption = ((NounTest)test).getCorrectOption();
        ViewGroup radioGroup = (ViewGroup)findViewById(R.id.radioGroup);
        int count = 0;
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View o = radioGroup.getChildAt(i);
            count = (o instanceof ToggleButton) ? count + 1 : count;
        }

        Map<Integer,Integer> mapCanDiasable = new HashMap<Integer,Integer>();
        for (int i = 0; i < count; i++) {
            int size = mapCanDiasable.size();
            if (i != correctOption) {
                mapCanDiasable.put(size, i);
            }
        }

        Random r = new Random();
        int randToLeave = r.nextInt( mapCanDiasable.size() );
        for(Map.Entry<Integer, Integer> entry : mapCanDiasable.entrySet()) {
            if (entry.getKey() != randToLeave) {
                int optionToDisable = entry.getValue();
                View o = radioGroup.getChildAt(optionToDisable);
                if (o instanceof ToggleButton) {
                    ToggleButton option = (ToggleButton)o;
                    option.setEnabled(false);
                    option.setTextColor( this.getResources().getColor(R.color.colorGreyDark));
                }
            }
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

        ImageView ivVectorImage = (ImageView) findViewById(R.id.img_50x50);
        ivVectorImage.setImageResource(R.mipmap.ic_50x50);

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
                option.setEnabled(true);
                option.setTextColor( this.getResources().getColor(R.color.colorBlackLight));
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
