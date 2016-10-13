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
import com.ripka.deutschwiederholung.models.deTests.NounTest;
import com.ripka.deutschwiederholung.models.deTests.Test;
import com.ripka.deutschwiederholung.models.deTests.PartizipIITest;

public class TestGen {
    static private Test lastGenerated = null;
    static public Test generateNounTest(List<List<String>> words) {
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
            List<String> options = TestOptions.getNounsOptions(articleNo);
            Collections.shuffle(options);

            NounTest test = new NounTest(record);
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

    static public Test generateVerbTest(List<List<String>> words) {
        Random rand = new Random();
        int index = rand.nextInt(words.size());

        List<String> record = words.get(index);
        PartizipIITest nextTest = new PartizipIITest(record);

        lastGenerated = nextTest;
        return nextTest;
    }

    static public Test getLastGeneratedTest() {
        return lastGenerated;
    }
}
