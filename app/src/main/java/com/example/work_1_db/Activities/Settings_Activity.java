package com.example.work_1_db.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.work_1_db.R;

import java.io.IOException;
import java.io.InputStream;

public class Settings_Activity extends AppCompatActivity {
    RadioButton radioButton_font_1, radioButton_font_2, radioButton_font_3, r1, r2, r3, rD;
    TextView txtTest, txtFont1, txtFont2, txtFont3;
    RadioGroup rg1, rg2;
    Button btnSave;
    String Font, fontSize, chosenFont, chosenFontSize;
    ConstraintLayout constraintLayout;
    public static final String PREF_SETTINGS_KEY = "com.example.workdb_SETTINGS";
    public static final String PREF_FONT = "selectedFont";
    public static final String PREF_FONT_SIZE = "selectedFontSize";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);
        setTitle(R.string.settings);
        findViews();
        fontSelect();
        fontSizeSelect();
        //setBackGround();

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_SETTINGS_KEY, MODE_PRIVATE);
        chosenFont = sharedPreferences.getString(PREF_FONT, "Default");
        chosenFontSize = sharedPreferences.getString(PREF_FONT_SIZE, "Default");

        switchedInfo(chosenFontSize, chosenFont);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(PREF_SETTINGS_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(PREF_FONT, Font);
                editor.putString(PREF_FONT_SIZE, fontSize);
                editor.apply();
                finish();
            }
        });

    }

    public void fontSelect() {
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.r_btn_font_default:
                        Font = "Default";
                        break;
                    case R.id.r_btn_font_1:
                        txtTest.setTypeface(Typeface.createFromAsset(getAssets(), "font/ALGER.TTF"));
                        Font = "font/ALGER.TTF";
                        break;
                    case R.id.r_btn_font_2:
                        txtTest.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCBLKAD.TTF"));
                        Font = "font/ITCBLKAD.TTF";
                        break;
                    case R.id.r_btn_font_3:
                        txtTest.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCKRIST.TTF"));
                        Font = "font/ITCKRIST.TTF";
                        break;
                }
            }
        });
    }

    public void fontSizeSelect() {
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.r_btn_font_size_1:
                        txtTest.setTextSize(13);
                        fontSize = "13";
                        break;
                    case R.id.r_btn_font_size_2:
                        txtTest.setTextSize(17);
                        fontSize = "17";
                        break;
                    case R.id.r_btn_font_size_3:
                        txtTest.setTextSize(20);
                        fontSize = "20";
                        break;
                }
            }
        });
    }

    void findViews() {

        r1 = findViewById(R.id.r_btn_font_1);
        r2 = findViewById(R.id.r_btn_font_2);
        r3 = findViewById(R.id.r_btn_font_3);
        rD = findViewById(R.id.r_btn_font_default);

        radioButton_font_1 = findViewById(R.id.r_btn_font_size_1);
        radioButton_font_2 = findViewById(R.id.r_btn_font_size_2);
        radioButton_font_3 = findViewById(R.id.r_btn_font_size_3);
        rg1 = findViewById(R.id.radioGroup1);
        rg2 = findViewById(R.id.radioGroup2);
        constraintLayout = findViewById(R.id.CL_ID_LAYOUT);
        txtTest = findViewById(R.id.txt_settings_test);
        btnSave = findViewById(R.id.save_settings);
        txtFont1 = findViewById(R.id.txt_font_1);
        txtFont2 = findViewById(R.id.txt_font_2);
        txtFont3 = findViewById(R.id.txt_font_3);

        txtFont1.setTypeface(Typeface.createFromAsset(getAssets(), "font/ALGER.TTF"));
        txtFont2.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCBLKAD.TTF"));
        txtFont3.setTypeface(Typeface.createFromAsset(getAssets(), "font/ITCKRIST.TTF"));

    }

    void setBackGround() {
        Drawable d;
        try {
            InputStream ims = getAssets().open("settings_bg.jpg");
            d = Drawable.createFromStream(ims, null);
        } catch (IOException ex) {
            return;
        }
        constraintLayout.setBackground(d);
    }

    void switchedInfo(String s1, String s2) {
        switch (s1) {
            case "13":
                radioButton_font_1.setChecked(true);
                break;
            case "17":
                radioButton_font_2.setChecked(true);
                break;
            case "20":
                radioButton_font_3.setChecked(true);
                break;
            default:
                break;
        }

        switch (s2) {
            case "font/ALGER.TTF":
                r1.setChecked(true);
                break;
            case "font/ITCBLKAD.TTF":
                r2.setChecked(true);
                break;
            case "font/ITCKRIST.TTF":
                r3.setChecked(true);
                break;
            case "Default":
                rD.setChecked(true);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if (chosenFont.equals(Font) && chosenFontSize.equals(fontSize)) {
            Settings_Activity.super.onBackPressed();
        } else if (Font != null && fontSize == null
                ||
                fontSize != null && Font == null
                ||
                fontSize != null && Font != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Settings_Activity.super.onBackPressed();
                    Font = null;
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Settings_Activity.this, "Make Sure to Save Before Exit", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setMessage("By Pressing 'Ok' your data will be lost...");
            AlertDialog alertDialog = builder.create();
            alertDialog.setTitle("Attention");
            alertDialog.show();
        } else if (Font == null && fontSize == null) {
            Settings_Activity.super.onBackPressed();
        }


    }


}

