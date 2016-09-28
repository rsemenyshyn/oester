package ripka.deutschwiederholung;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;


import ripka.deutschwiederholung.models.Test;
import ripka.deutschwiederholung.models.TestGen;
import ripka.deutschwiederholung.models.TestOptions;
import ripka.deutschwiederholung.models.TestResult;
import ripka.deutschwiederholung.models.WordsParser;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_start);

        setContentView(R.layout.activity_start);
    }
    @Override
    protected void onResume() {
        super.onResume();
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
        AlphaAnimation fadeOut = new AlphaAnimation(0.0f , 1.0f ) ;
        TextView textView = (TextView) findViewById(R.id.loadText);
        textView.startAnimation(fadeIn);
        textView.startAnimation(fadeOut);
        fadeIn.setDuration(2000);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(2000);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(1000+fadeIn.getStartOffset());
    }

    public void btnStart(View view) {
        view.setEnabled(false);
        Intent intent = new Intent(this, NounsActivity.class);
        startActivity(intent);
    }
}
