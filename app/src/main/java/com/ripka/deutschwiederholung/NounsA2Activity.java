package com.ripka.deutschwiederholung;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.ripka.deutschwiederholung.models.WordsParser;

import android.graphics.pdf.PdfRenderer;

/**
 * Created by ripka on 9/28/16.
 */

public class NounsA2Activity extends NounsActivity {
    @Override
    protected void afterCreate(boolean isRestored) {
        List<Integer> filesToParse = new ArrayList<>(
                Arrays.asList(R.raw.nouns_a1)
        );
        tests = new WordsParser(filesToParse);
        setNextTest(isRestored);
    }
}
