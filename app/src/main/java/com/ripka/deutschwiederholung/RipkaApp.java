package com.ripka.deutschwiederholung;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ripka on 9/28/16.
 */

public class RipkaApp  extends Application {
    private static Context context;
    public void onCreate(){
        super.onCreate();
        RipkaApp.context = getApplicationContext();
    }
    public static Context getAppContext(){
        return RipkaApp.context;
    }

    protected static FirebaseUser getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            String uid = user.getUid();
        }
        return user;
    }
}
