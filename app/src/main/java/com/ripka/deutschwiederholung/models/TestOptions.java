package com.ripka.deutschwiederholung.models;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ripka.deutschwiederholung.R;
import com.ripka.deutschwiederholung.RipkaApp;

/**
 * Created by ripka on 9/25/16.
 */

public class TestOptions {
    static private List<List<String>> Options = new ArrayList<List<String>>(
            Arrays.asList(
                    Arrays.asList("der", "die", "das", "die(pl)"),
                    Arrays.asList("den", "die", "das", "die(pl)"),
                    Arrays.asList("dem(m)", "der(f)", "dem(n)", "den")
            )
    );

    static private Map<String, Integer> Colors;
    static {
        Resources res = RipkaApp.getAppContext().getResources();
        Colors = new HashMap<String, Integer>();
        Colors.put("der", res.getColor(R.color.colorMale));
        Colors.put("die", res.getColor(R.color.colorFemale));
        Colors.put("das", res.getColor(R.color.colorNeutral));
        Colors.put("die(pl)", res.getColor(R.color.colorPlural));
        Colors.put("den", res.getColor(R.color.colorMale));
        Colors.put("dem(m)", res.getColor(R.color.colorMale));
        Colors.put("der(f)", res.getColor(R.color.colorFemale));
        Colors.put("dem(n)", res.getColor(R.color.colorNeutral));
        Colors.put("den(pl)", res.getColor(R.color.colorPlural));
    }
    static public int getOptionColor(String option) {
        return Colors.get(option);
    }
    static public List<String> getOption(int arcticelNo) {
        return Options.get(arcticelNo);
    }
}
