package com.example.jobtask.adapters;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtask.R;
import com.example.jobtask.model.DrinkResponce;
import com.example.jobtask.utilities.DataBaseHandler;
import com.example.jobtask.viewholders.DrinksViewHolder;
import com.example.jobtask.viewholders.FavouriteDrinksViewHolder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class FavouriteDrinkListAdapter extends RecyclerView.Adapter<FavouriteDrinksViewHolder> {

    private List<DrinkResponce> drinkList;

    public FavouriteDrinkListAdapter(List<DrinkResponce> drinkList)
    {
        this.drinkList=drinkList;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.list_item;
    }

    @NonNull
    @Override
    public FavouriteDrinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false);

        return new FavouriteDrinksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteDrinksViewHolder holder, int position) {

        final DrinkResponce singleDrink = drinkList.get(position);

        holder.getDrinkName().setText(singleDrink.getDrinkName());

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
      //  String imageURL = singleDrink.getDrinkImage();
        File file = new File(holder.itemView.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),""+singleDrink.getDrinkName()+".jpeg");

        Picasso.get().load(file).into(drinkImage);

        holder.getFavoriteStar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),"want to remove from favourite?",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
