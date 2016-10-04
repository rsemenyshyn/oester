package com.ripka.deutschwiederholung.models;

/**
 * Created by ripka on 9/22/16.
 */

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.ripka.deutschwiederholung.R;
import com.ripka.deutschwiederholung.RipkaApp;

public class TestGen {
    static private Test lastGenerated = null;
    static public Test generateNextTest(List<List<String>> words) {
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(RipkaApp.getAppContext());
        Boolean usePlural = shPref.getBoolean( RipkaApp.getAppContext().getString(R.string.nouns_plural), false);
        Test nextTest = null;
        Boolean genMore = true;

        while (genMore) {
            Random rand = new Random();
            int index = rand.nextInt(words.size());

            List<String> record = words.get(index);
            int articleNo = rand.nextInt(record.size() - 2);
            /* OLD WAY TO GET TEST CHOISES
            List<String> acticles = new ArrayList<String>();
            for (int i=0; i<words.size(); i++) {
                acticles.add( words.get(i).get(articleNo) );
            }
            List<String> options = new ArrayList(new HashSet(acticles));
            */
            List<String> options = TestOptions.getOption(articleNo);
            Collections.shuffle(options);

            Test test = new Test(record);
            test.setIntArticelNo(articleNo);
            test.setOptions(options);

            nextTest = test;
            if (usePlural || (!usePlural && !test.isPlural()) ) {
                genMore = false;
            }
        }

        lastGenerated = nextTest;
        return nextTest;
    }
    static public TestResult checkTest(int answerNo) {
        TestResult res = new TestResult();
        res = lastGenerated.isPassed(answerNo);
        return res;
    }
    static public Test getLastTest() {
        return lastGenerated;
    }
}
