package com.ripka.deutschwiederholung;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.ripka.deutschwiederholung.models.WordsParser;

/**
 * Created by ripka on 9/28/16.
 */

public class NounsA1Activity extends NounsActivity {
    @Override
    protected void afterCreate(boolean isRestored) {
        TextView txtTitle = (TextView) findViewById(R.id.name_activity);
        txtTitle.setText( getResources().getString(R.string.title_activity_nouns_a1) );

        List<Integer> filesToParse = new ArrayList<>(
                Arrays.asList(R.raw.nouns_a1)
        );
        tests = new WordsParser(filesToParse);
        setNextTest(isRestored);
    }
}
