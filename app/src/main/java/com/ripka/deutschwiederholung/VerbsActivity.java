package com.ripka.deutschwiederholung;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ripka.deutschwiederholung.models.TestGen;
import com.ripka.deutschwiederholung.models.TestResult;
import com.ripka.deutschwiederholung.models.WordsParser;
import com.ripka.deutschwiederholung.models.deTests.PartizipIITest;
import com.ripka.deutschwiederholung.models.deTests.Test;

/**
 * Created by ripka on 9/28/16.
 */

public class VerbsActivity extends NavActivity {
    protected WordsParser tests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewFlipper vf = (ViewFlipper)findViewById(R.id.vf);
        vf.setDisplayedChild(1);

        EditText editPerfect = (EditText)findViewById(R.id.verbs_perfect);
        editPerfect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Button btnCheck = (Button)findViewById(R.id.verbs_btn_go);
                if(s.length() > 0) btnCheck.setEnabled(true);
                else btnCheck.setEnabled(false);
            }
        });

        TextView txtWordTranslation = (TextView)findViewById(R.id.verbs_translate);
        txtWordTranslation.setVisibility(View.INVISIBLE);

        Button btnCheck = (Button)findViewById(R.id.verbs_btn_go);
        btnCheck.setVisibility(View.VISIBLE);
        btnCheck.setEnabled(false);

        afterCreate( savedInstanceState != null );
    }
    protected void afterCreate(boolean isRestored) {
        List<Integer> filesToParse = new ArrayList<>(
                Arrays.asList(R.raw.verbs_a1)
        );
        tests = new WordsParser(filesToParse);
        setNextTest(isRestored);
    }
    protected void setNextTest(boolean isRestored){
        Test test = TestGen.getLastGeneratedTest();
        if (!isRestored || test == null) {
            test = TestGen.generateVerbTest( tests.getWords() );
        }
        PartizipIITest myTest = (PartizipIITest)test;

        TextView txtWordCurrent = (TextView) findViewById(R.id.verbs_infinitive);
        TextView txtWordTranslation = (TextView)findViewById(R.id.verbs_translate);
        EditText txtPresent = (EditText) findViewById(R.id.verbs_present);
        EditText txtPast = (EditText) findViewById(R.id.verbs_past);
        EditText txtPerfect = (EditText) findViewById(R.id.verbs_perfect);

        txtWordCurrent.setText( myTest.getWord() );
        txtWordTranslation.setText( myTest.getTranslation() );
        txtPresent.setText( myTest.getPresent() );
        txtPast.setText( myTest.getPast() );
        txtPerfect.setText("");

        LinearLayout blockTranslate = (LinearLayout)findViewById(R.id.block_translate);
        if ( myTest.getTranslation()!=null || myTest.getTranslation().equals("-") || myTest.getTranslation().equals("") ){
            blockTranslate.setVisibility(View.INVISIBLE);
        } else {
            blockTranslate.setVisibility(View.VISIBLE);
        }
    }
    public void nextTest(View view) {
        setNextTest(false);

        TextView txtWordTranslation = (TextView)findViewById(R.id.verbs_translate);
        txtWordTranslation.setVisibility(View.INVISIBLE);

        TextView txtMessage = (TextView) findViewById(R.id.verbs_message);
        txtMessage.setVisibility(View.INVISIBLE);

        Button btnNext = (Button)findViewById(R.id.verbs_btn_next);
        btnNext.setVisibility(View.INVISIBLE);

        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
        if(checkBox.isChecked()){
            checkBox.toggle();
        }

        Button btnCheck = (Button)findViewById(R.id.verbs_btn_go);
        btnCheck.setVisibility(View.VISIBLE);
        btnCheck.setEnabled(false);

        EditText txtPerfect = (EditText) findViewById(R.id.verbs_perfect);
        txtPerfect.setEnabled(true);
    }
    public void checkTest(View view) {
        EditText txtPerfect = (EditText) findViewById(R.id.verbs_perfect);
        String answer = txtPerfect.getText().toString();
        txtPerfect.setEnabled(false);

        Test test = TestGen.getLastGeneratedTest();
        TestResult res = ((PartizipIITest)test).getResult( answer );

        TextView txtMessage = (TextView) findViewById(R.id.verbs_message);
        txtMessage.setText( res.message );
        if (res.isPassed) {
            txtMessage.setBackgroundColor( this.getResources().getColor(R.color.colorSuccess) );
        } else {
            txtMessage.setBackgroundColor( this.getResources().getColor(R.color.colorError) );
        }
        txtMessage.setVisibility(View.VISIBLE);

        Button btnCheck = (Button)findViewById(R.id.verbs_btn_go);
        btnCheck.setVisibility(View.INVISIBLE);

        Button btnNext = (Button)findViewById(R.id.verbs_btn_next);
        btnNext.setVisibility(View.VISIBLE);
    }
    public void onCheckedChanged(View view) {
        TextView txtWordTranslation = (TextView)findViewById(R.id.verbs_translate);
        if ( ((CheckBox)view).isChecked() ) {
            txtWordTranslation.setVisibility(View.VISIBLE);
        } else {
            txtWordTranslation.setVisibility(View.INVISIBLE);
        }
    }
}
