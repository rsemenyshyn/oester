package com.ripka.deutschwiederholung;

import android.widget.TextView;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import com.ripka.deutschwiederholung.models.WordsParser;

/**
 * Created by ripka on 9/28/16.
 */

public class NounsB1Activity extends NounsActivity {
    @Override
    protected void afterCreate(boolean isRestored) {
        TextView txtTitle = (TextView) findViewById(R.id.name_activity);
        txtTitle.setText( getResources().getString(R.string.title_activity_nouns_b1) );

        List<Integer> filesToParse = new ArrayList<>(
                Arrays.asList(R.raw.nouns_b1)
        );
        tests = new WordsParser(filesToParse);
        setNextTest(isRestored);
    }
}
