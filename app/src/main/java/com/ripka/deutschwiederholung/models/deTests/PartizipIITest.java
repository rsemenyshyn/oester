package com.ripka.deutschwiederholung.models.deTests;

import java.util.List;

/**
 * Created by ripka on 10/9/16.
 */

public class PartizipIITest extends Test {
    public PartizipIITest(List<String> _data) {
        super(_data);
    }
    protected static int PartizipIIindex = 2;
    protected static int PastIndex = 1;
    protected static int Presentindex = 0;

    // ------- abstract interface ---------
    protected boolean isPassed(String answer){
        return data.get(PartizipIIindex).equals( answer );
    }
    protected String getSuccessMessage(){
        return "Gut! Sie haben richtiges form geschriebt";
    }
    protected String getMistakeMessage(){
        return "Leider haben Sie falsches form geschriebt.\nRichtiges ist \""+ getPerfect() +"\"";
    }

    // -------------- GET -----------
    public String getPresent() {
        return data.get(Presentindex);
    }
    public String getPast() {
        return data.get(PastIndex);
    }
    public String getPerfect() {
        return data.get(PartizipIIindex);
    }
}
