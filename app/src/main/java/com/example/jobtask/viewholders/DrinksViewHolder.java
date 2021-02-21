package com.example.jobtask.viewholders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobtask.R;

public class DrinksViewHolder extends RecyclerView.ViewHolder {

    private ImageView drinkImage;
    private TextView drinkName;
    private TextView drinkRecipe;
    private CheckBox alcoholStatus;
    private ImageView favoriteStar;

    public DrinksViewHolder(@NonNull View itemView) {
        super(itemView);

        drinkName=itemView.findViewById(R.id.txt_drink_name);
        drinkRecipe=itemView.findViewById(R.id.txt_drink_recipe);
        drinkImage=itemView.findViewById(R.id.img_drink);
        favoriteStar=itemView.findViewById(R.id.img_star);
        alcoholStatus=itemView.findViewById(R.id.checkbox_alcohol);
    }

    public ImageView getDrinkImage() {
        return drinkImage;
    }

    public TextView getDrinkName() {
        return drinkName;
    }

    public TextView getDrinkRecipe() {
        return drinkRecipe;
    }

    public CheckBox getAlcoholStatus() {
        return alcoholStatus;
    }

    public ImageView getFavoriteStar() {
        return favoriteStar;
    }
}
