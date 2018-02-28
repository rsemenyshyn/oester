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
        return optionID >= 0 && options.get(optionID).equals( data.get(intArticelNo) );
    }
    protected String getSuccessMessage(){
        return "Gut! Sie haben richtigen Artikel ausgewählt";
    }
    protected String getMistakeMessage(){
        return "Leider falsch. Richtige ist \""+data.get(intArticelNo)+"\"";
    }

    // -------------- GET -----------
    public boolean isPlural(){
        return data.get(intArticelNo).toLowerCase().contains("(pl)");
    }
    public void setIntArticelNo(int _articleNo){
        intArticelNo = _articleNo;
    }
    public int getCorrectOption() {
        int optionCorrect = 0;
        String optionVal = data.get(intArticelNo);
        for (int i=0; i < options.size(); i++) {
            if ( options.get(i).equals(optionVal) ) {
                optionCorrect = i;
                break;
            }
        }
        return optionCorrect;
    }
}
