package com.example.work_1_db.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.work_1_db.Model.Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DbAryan extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "books.db";
    public static final String DB_TABLE = "course";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "name";
    private static final String KEY_CONTENT = "words";
    private static final String KEY_IMG_NAME = "image";
    private static final String KEY_FAVE = "fave";
    SQLiteDatabase database;

    Context context;

    public DbAryan(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    public void creteDataBase() {
        boolean dbExist = checkDataBase();
        if (!dbExist) {
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (checkDB != null)
            checkDB.close();

        return checkDB != null;
    }

    private void copyDataBase() throws IOException {
        InputStream inputStream = context.getAssets().open(DATABASE_NAME);
        File file = context.getDatabasePath(DATABASE_NAME);
        String string = file.getPath();
        OutputStream outputStream = new FileOutputStream(string);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDatabase() {
        File file = context.getDatabasePath(DATABASE_NAME);
        String string = file.getPath();
        database = SQLiteDatabase.openDatabase(string, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Model> Read()
    {
        ArrayList<Model> models = new ArrayList<>();
        database = this.getWritableDatabase();
        Cursor cursor = database.query("course",
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String Id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID));
            String Title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE));
            String Content = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT));
            String imageName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMG_NAME));
            String fave = cursor.getString(cursor.getColumnIndexOrThrow(KEY_FAVE));

            models.add(new Model(Id, Title, Content, imageName, fave));

            Log.d("TAGTEST", "Read: " + Id + Title + Content + imageName);
        }
        cursor.close();
        return models;
    }

    public ArrayList<Model> ReadFavorite() {
        ArrayList<Model> models = new ArrayList<>();
        database = this.getWritableDatabase();
        Cursor cursor = database.query("course",
                null, null, null, null, null, null);
        while (cursor.moveToNext())
        {
            String Id = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ID));
            String Title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE));
            String Content = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CONTENT));
            String imageName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_IMG_NAME));
            String fave = cursor.getString(cursor.getColumnIndexOrThrow(KEY_FAVE));

            if (fave.equals("1"))
                models.add(new Model(Id, Title, Content, imageName, fave));

            Log.d("TAGTEST", "Read: " + Id + Title + Content + imageName);
        }

        cursor.close();
        return models;

    }

    public boolean updateContactTest(String id, String fave) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FAVE, fave);

        database.update(DB_TABLE, values, "ID = ?", new String[]{id});
        return true;
    }




}
