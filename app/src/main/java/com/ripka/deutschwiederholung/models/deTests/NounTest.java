package com.ripka.deutschwiederholung.models.deTests;

import java.util.List;

/**
 * Created by ripka on 10/9/16.
 */

public class NounTest extends Test {
    protected int intArticelNo;
    public NounTest(List<String> _data) {
        super(_data);
    }

    // ------- abstract interface ---------
    protected boolean isPassed(String answer){
        int optionID = Integer.parseInt(answer);
        return options.get(optionID).equals( data.get(intArticelNo) );
    }
    protected String getSuccessMessage(){
        return "Gut! Sie haben richtigen Artikel ausgewählt";
    }
    protected String getMistakeMessage(){
        return "Leider haben Sie falschen Artikel gewählt. Richtige ist \""+data.get(intArticelNo)+"\"";
    }

    // -------------- GET -----------
    public boolean isPlural(){
        return data.get(intArticelNo).toLowerCase().contains("(pl)");
    }
    public void setIntArticelNo(int _articleNo){
        intArticelNo = _articleNo;
    }
}
