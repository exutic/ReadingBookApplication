package com.example.work_1_db.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.work_1_db.R;

import java.io.IOException;
import java.io.InputStream;

import static com.example.work_1_db.Activities.Settings_Activity.PREF_FONT;
import static com.example.work_1_db.Activities.Settings_Activity.PREF_FONT_SIZE;
import static com.example.work_1_db.Activities.Settings_Activity.PREF_SETTINGS_KEY;

public class ShowStory_Activity extends AppCompatActivity {
    TextView TXT_CONTENT;
    ImageView IMG_TITLE;
    String Title, Content, imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_story);

        TXT_CONTENT = findViewById(R.id.txt_content);
        IMG_TITLE = findViewById(R.id.img_title_showStory_id);

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_SETTINGS_KEY, MODE_PRIVATE);
        String chosenFont = sharedPreferences.getString(PREF_FONT, "Default");
        String chosenFontSize = sharedPreferences.getString(PREF_FONT_SIZE, "Default");

        if (chosenFont == null)
            chosenFont = "Default";
        switch (chosenFont) {
            case "Default":
                break;
            case "font/ALGER.TTF":
                TXT_CONTENT.setTypeface(Typeface.createFromAsset(getAssets(), "font/ALGER.TTF"));
                break;

            case "font/ITCBLKAD.TTF":
                TXT_CONTENT.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCBLKAD.TTF"));
                break;

            case "font/ITCKRIST.TTF":
                TXT_CONTENT.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCKRIST.TTF"));
                break;
        }

        if (chosenFontSize == null)
            chosenFontSize = "Default";
        switch (chosenFontSize) {
            case "Default":
                break;
            case 13 + "":
                TXT_CONTENT.setTextSize(13);
                break;

            case 17 + "":
                TXT_CONTENT.setTextSize(17);
                break;

            case 20 + "":
                TXT_CONTENT.setTextSize(20);
                break;
        }


        Intent intent = getIntent();
        Title = intent.getStringExtra("Title");
        Content = intent.getStringExtra("Content");
        imageName = intent.getStringExtra("imageName");
        //BackGround
        LinearLayout fr = findViewById(R.id.showStory_layout_id);
        try {
            InputStream ims = getAssets().open("evil_db.jpg");
            Drawable d = Drawable.createFromStream(ims, null);
            fr.setBackground(d);
        } catch (IOException ex) {
        }

        TXT_CONTENT.setText(Content);
        setTitle(Title);
        TXT_CONTENT.setMovementMethod(new ScrollingMovementMethod());

        Drawable d;
        try {
            InputStream ims = getAssets().open(imageName + ".jpg");
            d = Drawable.createFromStream(ims, null);
        } catch (IOException ex) {
            return;
        }
        IMG_TITLE.setImageDrawable(d);
    }
}



