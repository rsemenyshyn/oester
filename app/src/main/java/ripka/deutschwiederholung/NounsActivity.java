package ripka.deutschwiederholung;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ripka.deutschwiederholung.models.Test;
import ripka.deutschwiederholung.models.TestGen;
import ripka.deutschwiederholung.models.TestOptions;
import ripka.deutschwiederholung.models.TestResult;
import ripka.deutschwiederholung.models.WordsParser;

public class NounsActivity extends NavActivity {

    protected WordsParser tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewFlipper vf = (ViewFlipper)findViewById(R.id.vf);
        vf.setDisplayedChild(0);


        Button btnCheck = (Button)findViewById(R.id.app_btn_go);
        btnCheck.setVisibility(View.VISIBLE);
        btnCheck.setEnabled(false);

        RadioGroup ringtone_radio_group = (RadioGroup)findViewById(R.id.radioGroup);
        ringtone_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Button btnCheck = (Button)findViewById(R.id.app_btn_go);
                btnCheck.setEnabled(true);
            }
        });

        TextView textView = (TextView) findViewById(R.id.bannerText);
        textView.getBackground().setAlpha(85);
    }
    @Override
    protected void onStart() {
        super.onStart();

        List<Integer> filesToParse = new ArrayList<>(
                Arrays.asList(R.raw.nouns_a1)
        );
        tests = new WordsParser(filesToParse);
        setNextTest();
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

        Button btnNext = (Button)findViewById(R.id.app_btn_next);
        btnNext.setVisibility(View.INVISIBLE);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
        if(checkBox.isChecked()){
            checkBox.toggle();
        }

        Button btnCheck = (Button)findViewById(R.id.app_btn_go);
        btnCheck.setVisibility(View.VISIBLE);
        btnCheck.setEnabled(false);
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
                option.setTextColor( TestOptions.getOptionColor(strRadio) );
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

        if (res.isPassed) {
            txtMessage.setBackgroundColor( this.getResources().getColor(R.color.colorSuccess) );
        } else {
            txtMessage.setBackgroundColor( this.getResources().getColor(R.color.colorError) );
        }
    }
}
