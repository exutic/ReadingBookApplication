package com.example.work_1_db.Activities;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import com.example.work_1_db.Database.DbAryan;
import com.example.work_1_db.Model.Model;
import com.example.work_1_db.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Favorite_Activity extends AppCompatActivity {
    LinearLayout fr;
    ArrayList<Model> models = new ArrayList<>();
    public RecyclerView recyclerView;
    public ContactAdapter adapter;
    DbAryan newDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_);
        setBackGround();

        newDB = new DbAryan(this);

        models = newDB.ReadFavorite();

        setUpRecyclerView();


    }

    public void setUpRecyclerView() {
        //================================= <<<<<< Read COntact From DB and Import into Recycler
//        models.clear();
        recyclerView = findViewById(R.id.recyclerView_fave_id);
        adapter = new ContactAdapter(models, Favorite_Activity.this);
        recyclerView.setLayoutManager
                (new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contact_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_action_search_id);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    void setBackGround() {
        //Background
        fr = findViewById(R.id.layout_fave_id);
        // load image
        try {
            // get input stream
            InputStream ims = getAssets().open("evil_db.jpg");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            fr.setBackground(d);
            // set image to ImageView
        } catch (IOException ex) {
            return;
        }
    }
}
