package com.ripka.deutschwiederholung;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.ActivityManager;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ripka on 9/28/16.
 */

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected Integer VIEW_NOUNS = 0;
    protected Integer VIEW_VERBS = 1;
    protected Integer VIEW_GRAMM = 2;
    protected Integer VIEW_SEARCH = 3;
    protected ProgressBar mProgressMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "E-mail or SMS/MMS with screenshot can be send", Snackbar.LENGTH_LONG)
                        .setAction("Send", sendEmail(getWindow().getDecorView().getRootView())).show();
            }
        });

        View callSeacrh = (View) findViewById(R.id.activity_search);
        callSeacrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onSearchClick(view); }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View header = navigationView.getHeaderView(0);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.progress_linear);
        mProgressMain = (ProgressBar) header.findViewById(R.id.progressMain);
        mProgressMain.setProgressDrawable(drawable);
        mProgressMain.setSecondaryProgress(100);
        mProgressMain.setMax(100);
        mProgressMain.setProgress(100);

        if (!setLoggedUser()) {
            callLoginActivity();
        }
        Bitmap recentsIcon = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
        int colorPrimary = res.getColor(R.color.colorPrimary);
        ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(null, recentsIcon, colorPrimary);
        this.setTaskDescription(description);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!setLoggedUser()) {
            callLoginActivity();
        }
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(RipkaApp.getAppContext());
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        if (header != null) {
            TextView txtUserName = (TextView) header.findViewById(R.id.user_name);
            if (txtUserName != null) {
                String userName = shPref.getString(getString(R.string.display_name), "");
                txtUserName.setText(userName);
            }
            TextView txtUserEmail = (TextView) header.findViewById(R.id.user_email);
            if (txtUserEmail != null) {
                String userEmail = shPref.getString(getString(R.string.contact_email), "");
                txtUserEmail.setText(userEmail);
            }
            ImageView imgUserPhoto = (ImageView) header.findViewById(R.id.image_user);
            if (imgUserPhoto != null) {
                //imgUserPhoto.setVisibility(View.INVISIBLE);
                String photoSetting = shPref.getString(getString(R.string.user_photo), "");
                String demoLink = "https://www.xing.com/assets/frontend_minified/img/users/nobody_m.256x256.jpg";
                String photo = photoSetting.isEmpty() ? demoLink : photoSetting;
                if (!photo.isEmpty()) {
                    Picasso.with(this).load(photo).into(imgUserPhoto);
                    imgUserPhoto.setVisibility(View.VISIBLE);
                }
            }
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Boolean enableFab = shPref.getBoolean(getString(R.string.email_switcher), false);
        fab.setVisibility( enableFab ? View.VISIBLE : View.INVISIBLE );
    }

    public boolean setLoggedUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        LoginManager fb_login = LoginManager.getInstance();
        boolean isLoggedUser = (auth.getCurrentUser() != null) || (fb_login != null);
        if (auth.getCurrentUser() != null) {
            isLoggedUser = true;
            Uri userPhoto = auth.getCurrentUser().getPhotoUrl();
            String userEmail = auth.getCurrentUser().getEmail();
            String userName = auth.getCurrentUser().getDisplayName();
            String userPicture = (userPhoto == null || userPhoto.getPath() == null) ? "" : userPhoto.toString();
            if (userName == null || userName.isEmpty()) {
                String[] parts = userEmail.split("@");
                userName = parts[0].trim();
                userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
            }
            SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(RipkaApp.getAppContext());
            SharedPreferences.Editor editor = shPref.edit();
            editor.putString(getString(R.string.display_name), userName);
            editor.putString(getString(R.string.contact_email), userEmail);
            editor.putString(getString(R.string.user_photo), userPicture);
            editor.apply();
        }
        return isLoggedUser;
    }
    public boolean logOut() {
        boolean hasLoggedOut = false;
        boolean isLoggedUser = setLoggedUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        LoginManager fb_login = LoginManager.getInstance();
        if (isLoggedUser) {
            auth.signOut();
            hasLoggedOut = true;
        }
        if (isLoggedUser) {
            fb_login.logOut();
            hasLoggedOut = true;
        }
        return hasLoggedOut;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_logout) {
            if ( logOut() ) {
                callLoginActivity();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void callLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_nouns_all) {
            Intent intent = new Intent(this, NounsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_verbs_all) {
            Intent intent = new Intent(this, VerbsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_nouns_a1) {
            Intent intent = new Intent(this, NounsA1Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_verbs_a1) {
            Intent intent = new Intent(this, VerbsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_grammar_a1) {
            Intent intent = new Intent(this, GrammatikA1Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_nouns_a2) {
            Intent intent = new Intent(this, NounsA2Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_nouns_b1) {
            Intent intent = new Intent(this, NounsB1Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_nouns_b2) {
            Intent intent = new Intent(this, NounsB2Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_verbs_a2) {
            Intent intent = new Intent(this, VerbsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_grammar_a2) {
            Intent intent = new Intent(this, GrammatikA2Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // ----------- GLOBAL UI -----------
    public void onSearchClick(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    public void sendScreenShot() {
        View view = getWindow().getDecorView().getRootView();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        try {
            File imagePath = File.createTempFile("screenshot",".png", getExternalCacheDir());
            FileOutputStream fos;
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            imagePath.setReadable(true, false);
            sendMail(imagePath);
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    public void sendMail(File file) {
        SharedPreferences shPref = PreferenceManager.getDefaultSharedPreferences(RipkaApp.getAppContext());
        String userName = shPref.getString(getString(R.string.display_name), "");
        String userEmail = shPref.getString(getString(R.string.contact_email), "");

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { "semenyshyn.r@gmail.com" });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback from customer");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "This is an autogenerated mail from Ripka's ??ster (Deutsch Wiederholung) app. \n" +
                "My user name: "+ userName +". And my e-mail for app use is: " + userEmail +".\n");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
    public View.OnClickListener sendEmail(View view) {
        sendScreenShot();
        return null;
    }
}
