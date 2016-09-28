package ripka.deutschwiederholung.models;

/**
 * Created by ripka on 9/22/16.
 */
import ripka.deutschwiederholung.MainActivity;
import ripka.deutschwiederholung.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WordsParser {
    protected List<List<String>> words;

    public WordsParser (Context context) {
        words = LoadWords(context);
    }

    public List<List<String>> getWords(){
        return words;
    }

    public List<List<String>> LoadWords(Context context){
        List<List<String>> result = new ArrayList<List<String>>();
        InputStream is = context.getResources().openRawResource(R.raw.deutsch);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8") );
            String line = reader.readLine();
            while (line != null) {
                String[] splitArray = line.split("\\t+");
                List<String> list = Arrays.asList(splitArray);
                result.add(list);

                line = reader.readLine();
            }
        } catch (IOException ex) {
            new AlertDialog.Builder(context)
                    .setTitle("Error")
                    .setMessage("Sorry, we got unrecognized error :(")
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return result;
    }
}
