package com.example.jobtask.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class DrinkResponce {

    @SerializedName("strDrink")
    private String drinkName;

    @SerializedName("strInstructions")
    private String drinkRecipe;

    @SerializedName("strAlcoholic")
    private String alcoholStatus;

    @SerializedName("strDrinkThumb")
    private String drinkImage;

    @SerializedName("drinks")
    public JsonArray allDrinks;



    public String getDrinkName() {
        return drinkName;
    }


    public JsonArray getAllDrinks() {
        return allDrinks;
    }

    public String getDrinkRecipe() {
        return drinkRecipe;
    }

    public String getAlcoholStatus() {
        return alcoholStatus;
    }

    public String getDrinkImage() {
        return drinkImage;
    }
}
