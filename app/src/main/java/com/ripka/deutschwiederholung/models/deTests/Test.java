package com.ripka.deutschwiederholung.models.deTests;

import com.ripka.deutschwiederholung.models.TestResult;

import java.util.List;

/**
 * Created by ripka on 9/22/16.
 */

abstract public class Test {
    protected List<String> data;
    protected List<String> options;

    public Test(List<String> _data) {
        data = _data;
    }
    public TestResult getResult(String answer) {
        TestResult res = new TestResult();
        res.isPassed = isPassed(answer);

        if (res.isPassed) {
            res.message = getSuccessMessage();
        } else {
            res.message = getMistakeMessage();
        }
        return res;
    }
    protected abstract boolean isPassed(String answer);
    protected abstract String getSuccessMessage();
    protected abstract String getMistakeMessage();

    // -------------- SET ----------
    public void setOptions(List<String> _options) {
        options = _options;
    }

    // -------------- GET ----------
    public String getWord(){
        return data.get( getWordIndex() );
    }
    public String getTranslation() {
        return data.get( getTranslationIndex() );
    }
    public List<String> getOptions() {
        return options;
    }

    // ------------- internal GET --------
    protected int getWordIndex() {
        return data.size()-2;
    }
    protected int getTranslationIndex() {
        return data.size()-1;
    }
}


