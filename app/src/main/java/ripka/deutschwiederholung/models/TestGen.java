package ripka.deutschwiederholung.models;

/**
 * Created by ripka on 9/22/16.
 */

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestGen {
    static private Test lastGenerated = null;
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
        List<String> options = TestOptions.getOption(articleNo);
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
    static public Test getLastTest() {
        return lastGenerated;
    }
}
