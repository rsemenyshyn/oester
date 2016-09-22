package com.example.ripka.deutschwiederholung.models;

/**
 * Created by ripka on 9/22/16.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class TestGen {
    static private List<List<String>> Options = new ArrayList<List<String>>(
            Arrays.asList(
                    Arrays.asList("der", "die", "das", "die"),
                    Arrays.asList("den", "die", "das", "die"),
                    Arrays.asList("dem", "der", "dem", "den"),
                    Arrays.asList("ein", "eine","ein", "-"),
                    Arrays.asList("einen", "eine","ein", "-"),
                    Arrays.asList("einem", "einer","einem", "einen")
            )
    );

    static private Test lastGenerated;
    static public Test generateNextTest(List<List<String>> words) {
        Random rand = new Random();
        int index = rand.nextInt( words.size() );

        List<String> record = words.get(index);
        int articleNo = rand.nextInt( record.size()-2 );
        /* OLD WAY TO GET TEST CHOISES
        List<String> acticles = new ArrayList<String>();
        for (int i=0; i<words.size(); i++) {
            acticles.add( words.get(i).get(articleNo) );
        }
        List<String> options = new ArrayList(new HashSet(acticles));
        */
        List<String> options = Options.get(articleNo);
        Collections.shuffle(options);

        Test test = new Test( record );
        test.setIntArticelNo(articleNo);
        test.setOptions(options);

        lastGenerated = test;
        return test;
    }
    static public TestResult checkTest(int answerNo) {
        TestResult res = new TestResult();
        res = lastGenerated.isPassed(answerNo);
        return res;
    }
}
