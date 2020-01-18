package com.example.work_1_db.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.work_1_db.Database.DbAryan;
import com.example.work_1_db.Model.Model;
import com.example.work_1_db.R;
import com.example.work_1_db.Splash;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.work_1_db.Activities.Settings_Activity.PREF_FONT;
import static com.example.work_1_db.Activities.Settings_Activity.PREF_FONT_SIZE;
import static com.example.work_1_db.Activities.Settings_Activity.PREF_SETTINGS_KEY;

class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Model> contactParentsList;
    private Context mContext;
    DbAryan dbNEw;
    String chosenFont, chosenFontSize;
    SharedPreferences sharedPreferences;
    private List<Model> contactParentsListFull;


    public ContactAdapter(List<Model> contactParentsList, Context mContext)
    {
        this.contactParentsList = contactParentsList;
        contactParentsListFull = new ArrayList<>(contactParentsList);
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your DataSet at this position
        // - replace the contents of the view with that element
        //Making Object
        dbNEw = new DbAryan(mContext);
        final ViewHolder viewHolder = (ViewHolder) holder;
        final Model modelTest = (Model) contactParentsList.get(position);
        viewHolder.txtTitle.setText(modelTest.getTitle());

        //Declared in Fields
        sharedPreferences = mContext.getSharedPreferences(PREF_SETTINGS_KEY, MODE_PRIVATE);
        chosenFont = sharedPreferences.getString(PREF_FONT, "Default");
        chosenFontSize = sharedPreferences.getString(PREF_FONT_SIZE, "Default");

        if (chosenFont == null)
            chosenFont = "Default";
        switch (chosenFont) {
            case "Default":
                break;
            case "font/ALGER.TTF":
                viewHolder.txtTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "font/ALGER.TTF"));
                break;

            case "font/ITCBLKAD.TTF":
                viewHolder.txtTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "font/ITCBLKAD.TTF"));
                break;

            case "font/ITCKRIST.TTF":
                viewHolder.txtTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "font/ITCKRIST.TTF"));
                break;
        }

        if (chosenFontSize == null)
            chosenFontSize = "Default";
        switch (chosenFontSize) {
            case "Default":
                break;
            case 13 + "":
                viewHolder.txtTitle.setTextSize(13);
                break;

            case 17 + "":
                viewHolder.txtTitle.setTextSize(17);
                break;

            case 20 + "":
                viewHolder.txtTitle.setTextSize(20);
                break;
        }


        //Adding Image
        Drawable d;
        try {
            // get input stream
            InputStream ims = mContext.getAssets().open(modelTest.getImageName() + ".jpg");
            // load image as Drawable
            d = Drawable.createFromStream(ims, null);
            // set image to ImageView
        } catch (IOException ex) {
            return;
        }
        viewHolder.imgEachTitle.setImageDrawable(d);
        viewHolder.rltv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, ShowStory_Activity.class);
                intent.putExtra("Title", modelTest.getTitle());
                intent.putExtra("Content", modelTest.getContent());
                intent.putExtra("imageName", modelTest.getImageName());
                mContext.startActivity(intent);
            }
        });
        viewHolder.imgFAVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (modelTest.getFave()) {
                    case "0":
                        modelTest.setFave("1");
                        viewHolder.imgFAVE.setImageResource(R.drawable.full_star);
                        Splash.db.updateContactTest(modelTest.getId(),modelTest.getFave());
                        Toast.makeText(mContext,R.string.add_to_favorite, Toast.LENGTH_SHORT).show();
                        break;
                    case "1":
                        modelTest.setFave("0");
                        viewHolder.imgFAVE.setImageResource(R.drawable.empry_star);
                        Splash.db.updateContactTest(modelTest.getId(),modelTest.getFave());
                        Toast.makeText(mContext, R.string.remove_from_fave, Toast.LENGTH_SHORT).show();
                        break;
                }

            }

        });

        switch (modelTest.getFave())
        {
            case "0":
                viewHolder.imgFAVE.setImageResource(R.drawable.empry_star);
                break;
            case "1":
                viewHolder.imgFAVE.setImageResource(R.drawable.full_star);
                break;
        }
    }

    // Return the size of your DataSet (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contactParentsList.size();
    }

    //Flow
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Model> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(contactParentsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Model item : contactParentsListFull) {
                    if (item.getTitle().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }else if (item.getContent().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactParentsList.clear();
            contactParentsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public ImageView imgEachTitle;
        LinearLayout rltv;
        ImageView imgFAVE;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            imgEachTitle = itemView.findViewById(R.id.img_each_title_id);
            rltv = itemView.findViewById(R.id.rltv);
            imgFAVE = itemView.findViewById(R.id.img_favorite_select_id);
        }

    }
}


