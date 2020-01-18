package com.example.work_1_db.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.work_1_db.R;

import java.io.IOException;
import java.io.InputStream;

import static com.example.work_1_db.Activities.Settings_Activity.PREF_FONT;
import static com.example.work_1_db.Activities.Settings_Activity.PREF_SETTINGS_KEY;

public class Menu_Activity extends AppCompatActivity {
    TextView btnMainList, btnFavoriteList, btnSettings;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_);
        findVIEWS();

        Drawable d;
        try {
            InputStream ims = getAssets().open("evil_db.jpg");
            d = Drawable.createFromStream(ims, null);
        } catch (IOException ex) {
            return;
        }
        linearLayout.setBackground(d);

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_SETTINGS_KEY, MODE_PRIVATE);
        String chosenFont = sharedPreferences.getString(PREF_FONT, "Default");

        if (chosenFont == null)
            chosenFont = "Default";

        switch (chosenFont) {
            case "Default":
                break;
            case "font/ALGER.TTF":
                btnMainList.setTypeface(Typeface.createFromAsset(getAssets(), "font/ALGER.TTF"));
                btnFavoriteList.setTypeface(Typeface.createFromAsset(getAssets(), "font/ALGER.TTF"));
                btnSettings.setTypeface(Typeface.createFromAsset(getAssets(), "font/ALGER.TTF"));
                break;

            case "font/ITCBLKAD.TTF":
                btnMainList.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCBLKAD.TTF"));
                btnFavoriteList.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCBLKAD.TTF"));
                btnSettings.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCBLKAD.TTF"));
                break;

            case "font/ITCKRIST.TTF":
                btnMainList.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCKRIST.TTF"));
                btnFavoriteList.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCKRIST.TTF"));
                btnSettings.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCKRIST.TTF"));

                break;
        }
    }

    public void clicked(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btn_list_id:
                startActivity(new Intent(Menu_Activity.this, MainActivity.class));
                break;

            case R.id.btn_fave_list_id:
                startActivity(new Intent(Menu_Activity.this, Favorite_Activity.class));
                break;

            case R.id.btn_settings_id:
                startActivity(new Intent(Menu_Activity.this, Settings_Activity.class));
                break;

            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void findVIEWS() {
        btnMainList = findViewById(R.id.btn_list_id);
        btnFavoriteList = findViewById(R.id.btn_fave_list_id);
        btnSettings = findViewById(R.id.btn_settings_id);
        linearLayout = findViewById(R.id.menu_layout_id);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        super.onBackPressed();
    }
}
