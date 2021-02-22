package com.example.jobtask.adapters;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtask.R;
import com.example.jobtask.model.DrinkResponce;
import com.example.jobtask.utilities.DataBaseHandler;
import com.example.jobtask.viewholders.DrinksViewHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class DrinkListAdapter extends RecyclerView.Adapter<DrinksViewHolder> {

    private List<DrinkResponce> drinkList;

    public DrinkListAdapter(List<DrinkResponce> drinkList)
    {
        this.drinkList=drinkList;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.list_item;
    }

    @NonNull
    @Override
    public DrinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);

        return new DrinksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinksViewHolder holder, int position) {

        final DrinkResponce singleDrink = drinkList.get(position);

        holder.getDrinkName().setText(singleDrink.getDrinkName());
        holder.getFavoriteStar().setBackgroundResource(R.drawable.grey_star);

        if(singleDrink.getAlcoholStatus().equals("Alcoholic"))
        {
            holder.getAlcoholStatus().setChecked(true);
        }

        if(singleDrink.getAlcoholStatus().equals("Non alcoholic"))
        {
            holder.getAlcoholStatus().setChecked(false);
        }

        holder.getDrinkRecipe().setText(singleDrink.getDrinkRecipe());
        ImageView drinkImage = holder.getDrinkImage();
        String imageURL = singleDrink.getDrinkImage();
        Picasso.get().load(imageURL).into(drinkImage);

        holder.getFavoriteStar().setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),"Added to Favourites",Toast.LENGTH_SHORT).show();
                DataBaseHandler dataBaseHandler = new DataBaseHandler(v.getContext());
                dataBaseHandler.insertFavorite(singleDrink);
                holder.getFavoriteStar().setBackgroundResource(R.drawable.ic_round_star_24);

                File file = new File(v.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),""+singleDrink.getDrinkName()+".jpeg");

                try{
                    DownloadManager.Request dRequest = new DownloadManager.Request(Uri.parse(imageURL))
                            .setTitle(singleDrink.getDrinkName())
                            .setDescription("Downloading")
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
                            .setDestinationUri(Uri.fromFile(file))
                            .setRequiresCharging(false)
                            .setAllowedOverMetered(true)
                            .setAllowedOverRoaming(true);
                            //.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);

                            DownloadManager downloadManager = (DownloadManager) v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                            long referenceID= downloadManager.enqueue(dRequest);


                }catch (Exception e)
                {
                    Log.e("scoped",e.getMessage());

                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
