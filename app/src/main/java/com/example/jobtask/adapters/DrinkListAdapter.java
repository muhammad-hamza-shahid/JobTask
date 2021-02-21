package com.example.jobtask.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtask.R;
import com.example.jobtask.model.DrinkResponce;
import com.example.jobtask.utilities.DataBaseHandler;
import com.example.jobtask.viewholders.DrinksViewHolder;

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
        holder.getAlcoholStatus().isChecked();
        holder.getDrinkRecipe().setText(singleDrink.getDrinkRecipe());
        holder.getFavoriteStar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(),"favourite Clicked",Toast.LENGTH_SHORT).show();
                DataBaseHandler dataBaseHandler = new DataBaseHandler(v.getContext());
                dataBaseHandler.insertFavorite(singleDrink);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
