package ripka.deutschwiederholung;

import android.app.Application;
import android.content.Context;

/**
 * Created by ripka on 9/28/16.
 */

public class RipkaApp  extends Application{
    private static Context context;
    public void onCreate(){
        super.onCreate();
        RipkaApp.context = getApplicationContext();
    }
    public static Context getAppContext(){
        return RipkaApp.context;
    }
}
