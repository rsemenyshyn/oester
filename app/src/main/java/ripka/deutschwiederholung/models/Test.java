package ripka.deutschwiederholung.models;

import java.util.List;

/**
 * Created by ripka on 9/22/16.
 */

public class Test {
    protected List<String> data;
    protected int intArticelNo;
    protected List<String> options;

    public Test(List<String> _data) {
        data = _data;
    }
    public TestResult isPassed(int optionID) {
        TestResult res = new TestResult();
        res.isPassed = options.get(optionID).equals( data.get(intArticelNo) );
        if (res.isPassed) {
            res.message = "Gut! Sie haben richtigen Artikel ausgewählt";
        } else {
            res.message = "Leider haben Sie falschen Artikel gewählt. Richtige ist \""+data.get(intArticelNo)+"\"";
        }
        return res;
    }
    public boolean isPlural(){
        return data.get(intArticelNo).toLowerCase().contains("(pl)");
    }

    // -------------- SET ----------
    public void setOptions(List<String> _options) {
        options = _options;
    }
    public void setIntArticelNo(int _articleNo){
        intArticelNo = _articleNo;
    }

    // -------------- GET ----------
    public String getWord(){
        return data.get( data.size()-2 );
    }
    public String getTranslation() {
        return data.get( data.size()-1 );
    }
    public List<String> getOptions() {
        return options;
    }
}
