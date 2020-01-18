package com.example.work_1_db;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.work_1_db.Activities.Menu_Activity;
import com.example.work_1_db.Database.DbAryan;

import java.io.IOException;
import java.io.InputStream;

public class Splash extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1500;
    public static DbAryan db;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        ImageView img = findViewById(R.id.splashscreen);

        db = new DbAryan(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            db.creteDataBase();
            db.openDatabase();

            // mark first time has ran.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
        db.Read();




        Drawable d;
        try {
            InputStream ims = getAssets().open("splash.jpg");
            d = Drawable.createFromStream(ims, null);
        } catch (IOException ex) {
            return;
        }
        img.setImageDrawable(d);


//         New Handler to start the Menu-Activity
//         and close this Splash-Screen after some seconds.
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
//                 Create an Intent that will start the Menu-Activity.
                Intent mainIntent = new Intent(Splash.this, Menu_Activity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
