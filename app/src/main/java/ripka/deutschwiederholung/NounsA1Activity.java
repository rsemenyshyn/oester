package ripka.deutschwiederholung;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ripka.deutschwiederholung.models.WordsParser;

/**
 * Created by ripka on 9/28/16.
 */

public class NounsA1Activity extends NounsActivity {
    @Override
    protected void onStart() {
        super.onStart();
        List<Integer> filesToParse = new ArrayList<Integer>(
                Arrays.asList(R.raw.nouns_a1)
        );
        tests = new WordsParser(filesToParse);
        setNextTest();
    }
}
